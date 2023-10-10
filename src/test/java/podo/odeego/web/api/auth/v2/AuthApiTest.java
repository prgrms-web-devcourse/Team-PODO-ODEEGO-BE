package podo.odeego.web.api.auth.v2;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.http.Cookie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import podo.odeego.config.TestRedisConfig;
import podo.odeego.domain.member.entity.MemberType;
import podo.odeego.web.auth.dto.CustomLoginRequest;
import podo.odeego.web.auth.dto.LoginMemberInfoResponse;
import podo.odeego.web.auth.dto.LoginResponse;
import podo.odeego.web.auth.dto.ReissueResponse;
import podo.odeego.web.auth.service.AuthService;

@SpringBootTest(classes = TestRedisConfig.class)
@AutoConfigureMockMvc
class AuthApiTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private AuthService authService;

	@Test
	@DisplayName("소셜 로그인에 성공할 경우 response body에 Access Token을, cookie에 Refresh Token을 응답합니다.")
	void socialLogin() throws Exception {
		//given
		LoginResponse loginResponse = LoginResponse.of(
			"access-token",
			"refresh-token-string",
			new LoginMemberInfoResponse(1L, "profileImageUrl", MemberType.PRE)
		);
		when(authService.socialLogin("oAuth2Token")).thenReturn(loginResponse);

		// when
		ResultActions result = mockMvc.perform(
			MockMvcRequestBuilders.post("/api/v2/auth/login/oauth2")
				.header(HttpHeaders.AUTHORIZATION, "oAuth2Token")
		);

		//then
		result.andExpect(status().isOk())
			.andExpect(cookie().value("refreshToken", "refresh-token-string"))
			.andExpect(cookie().httpOnly("refreshToken", true))
			.andExpect(cookie().secure("refreshToken", true))
			.andExpect(jsonPath("$.accessToken").value("access-token"))
			.andDo(print());
	}

	@Test
	@DisplayName("아이디/비밀번호 로그인에 성공할 경우 response body에 Access Token을, cookie에 Refresh Token을 응답합니다.")
	void customLogin() throws Exception {
		//given
		LoginResponse loginResponse = LoginResponse.of(
			"access-token",
			"refresh-token-string",
			new LoginMemberInfoResponse(1L, "profileImageUrl", MemberType.PRE)
		);
		CustomLoginRequest customLoginRequest = new CustomLoginRequest("username", "password");
		when(authService.customLogin(customLoginRequest)).thenReturn(loginResponse);

		// when
		ResultActions result = mockMvc.perform(
			MockMvcRequestBuilders.post("/api/v2/auth/login/custom")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customLoginRequest))
		);

		//then
		result.andExpect(status().isOk())
			.andExpect(cookie().value("refreshToken", "refresh-token-string"))
			.andExpect(cookie().httpOnly("refreshToken", true))
			.andExpect(cookie().secure("refreshToken", true))
			.andExpect(jsonPath("$.accessToken").value("access-token"))
			.andDo(print());
	}

	@Test
	@DisplayName("Refresh Token으로 Access Token 재발급 요청을 보낼 경우 response body에 Access Token, cookie에 RefreshToken을 응답합니다.")
	void reissue() throws Exception {
		//given
		String refreshToken = "refresh-token-string";
		String accessToken = "access-token-string";
		when(authService.reissue(refreshToken)).thenReturn(new ReissueResponse(accessToken, refreshToken));

		// when
		ResultActions result = mockMvc.perform(
			MockMvcRequestBuilders.post("/api/v2/auth/reissue")
				.cookie(new Cookie("refreshToken", refreshToken))
		);

		//then
		result.andExpect(status().isOk())
			.andExpect(cookie().value("refreshToken", "refresh-token-string"))
			.andExpect(cookie().httpOnly("refreshToken", true))
			.andExpect(cookie().secure("refreshToken", true))
			.andExpect(jsonPath("$.accessToken").value("access-token-string"))
			.andDo(print());
	}
}