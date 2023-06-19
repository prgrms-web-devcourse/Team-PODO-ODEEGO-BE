package podo.odeego.web.auth.service;

import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import podo.odeego.config.TestRedisConfig;
import podo.odeego.domain.member.entity.MemberType;
import podo.odeego.infra.openapi.kakao.KakaoClient;
import podo.odeego.infra.openapi.kakao.dto.KakaoProfileResponse;
import podo.odeego.web.auth.dto.LoginResponse;

@SpringBootTest(classes = TestRedisConfig.class)
@ExtendWith(MockitoExtension.class)
public class OAuth2ServiceIntegrityTest {

	Logger log = LoggerFactory.getLogger(OAuth2ServiceIntegrityTest.class);

	@Autowired
	AuthService authService;

	@MockBean
	KakaoClient kakaoClient;

	@DisplayName("사용자가 OAuth2 로그인을 진행하면 JWT 형식의 Access Token과 UUID 형식의 Refresh Token을 발급받습니다.")
	@Test
	public void login() {
		//given
		KakaoProfileResponse kakaoProfileResponse = KakaoProfileResponse.forTest(1L, "profileImageUrl");
		when(kakaoClient.getUserInfo("oAuth2Token")).thenReturn(kakaoProfileResponse);

		//when
		LoginResponse loginResponse = authService.socialLogin("oAuth2Token");
		MemberType expectedMemberType = loginResponse.getMemberType();
		String accessToken = loginResponse.getAccessToken();
		String refreshToken = loginResponse.getRefreshToken();

		//then
		log.info("accessToken: {}", accessToken);
		log.info("refreshToken: {}", refreshToken);
		Assertions.assertThat(expectedMemberType).isEqualTo(MemberType.PRE);
	}
}
