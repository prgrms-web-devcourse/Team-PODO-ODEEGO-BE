package podo.odeego.web.api.auth.v2;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import podo.odeego.config.TestRedisConfig;
import podo.odeego.domain.member.entity.MemberType;
import podo.odeego.web.auth.dto.LoginMemberInfoResponse;
import podo.odeego.web.auth.dto.LoginResponse;
import podo.odeego.web.auth.service.AuthService;

@SpringBootTest(classes = TestRedisConfig.class)
@AutoConfigureMockMvc
class AuthApiTest {

	@Autowired
	private MockMvc mockMvc;

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
			.andExpect(cookie().exists("refresh-token"))
			.andExpect(cookie().value("refresh-token", "refresh-token-string"))
			.andExpect(cookie().httpOnly("refresh-token", true))
			.andExpect(cookie().secure("refresh-token", true))
			.andExpect(jsonPath("$.accessToken").value("access-token"));
	}

	@Test
	@DisplayName("아이디/비밀번호 로그인에 성공할 경우 response body에 Access Token을, cookie에 Refresh Token을 응답합니다.")
	void customLogin() {
		//given

		//when

		//then

	}

	@Test
	@DisplayName("Refresh Token으로 Access Token 재발급 요청을 보낼 경우 response body에 Access Token을 응답합니다.")
	void reissue() {
		//given

		//when

		//then

	}
}