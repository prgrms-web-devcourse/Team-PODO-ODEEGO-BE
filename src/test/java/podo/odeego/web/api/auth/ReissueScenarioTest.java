package podo.odeego.web.api.auth;

import static org.apache.http.HttpHeaders.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.http.Cookie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import podo.odeego.config.TestRedisConfig;
import podo.odeego.domain.member.dto.MemberSignUpRequest;
import podo.odeego.infra.openapi.kakao.KakaoClient;
import podo.odeego.infra.openapi.kakao.dto.KakaoProfileResponse;
import podo.odeego.web.auth.dto.LoginResponse;

@ActiveProfiles("reissue-test")
@SpringBootTest(classes = TestRedisConfig.class)
@AutoConfigureMockMvc
class ReissueScenarioTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private KakaoClient kakaoClient;

	@DisplayName("사용자가 로그인한 후 Access Token이 만료되면 RefreshToken을 통해 재발급받습니다.")
	@Test
	public void reissueAfterAccessTokenExpired() throws Exception {
		//given: 사용자가 로그인한 후
		KakaoProfileResponse kakaoProfileResponse = KakaoProfileResponse.forTest(111L, "sdf");
		when(kakaoClient.getUserInfo("oAuth2Token"))
			.thenReturn(kakaoProfileResponse);

		MvcResult result = mockMvc.perform(post("/api/v2/auth/login/oauth2")
				.header(AUTHORIZATION, "oAuth2Token"))
			.andExpect(status().isOk())
			.andReturn();
		String refreshToken = result.getResponse().getCookie("refreshToken").getValue();
		String content = result.getResponse().getContentAsString();

		//when: access token이 만료되었다면
		LoginResponse loginResponse = objectMapper.readValue(content, LoginResponse.class);
		String signUpRequest = objectMapper.writeValueAsString(new MemberSignUpRequest("닉네임123", "강남역"));
		mockMvc.perform(patch("/api/v1/members/sign-up")
				.content(signUpRequest)
				.contentType(MediaType.APPLICATION_JSON)
				.header(AUTHORIZATION, "Bearer " + loginResponse.getAccessToken()))
			.andExpect(status().isUnauthorized())
			.andDo(print());

		//then: refresh token을 통해 재발급받을 수 있습니다
		mockMvc.perform(post("/api/v2/auth/reissue")
				.header(AUTHORIZATION, loginResponse.getAccessToken())
				.cookie(new Cookie("refreshToken", refreshToken)))
			.andExpect(status().isOk())
			.andDo(print());
	}
}