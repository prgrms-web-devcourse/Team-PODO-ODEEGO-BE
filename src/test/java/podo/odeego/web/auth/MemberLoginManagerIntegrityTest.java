package podo.odeego.web.auth;

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
import podo.odeego.domain.member.dto.MemberJoinResponse;
import podo.odeego.domain.member.entity.MemberType;
import podo.odeego.web.api.auth.dto.MemberLoginResponse;

@SpringBootTest(classes = TestRedisConfig.class)
@ExtendWith(MockitoExtension.class)
public class MemberLoginManagerIntegrityTest {

	Logger log = LoggerFactory.getLogger(MemberLoginManagerIntegrityTest.class);

	@Autowired
	MemberLoginManager memberLoginManager;

	@MockBean
	MemberJoinManager memberJoinManager;

	@DisplayName("사용자가 로그인을 진행하면 JWT 형식의 Access Token과 UUID 형식의 Refresh Token을 발급받습니다.")
	@Test
	public void login() {
		//given
		MemberJoinResponse joinResponse = new MemberJoinResponse(1L, "profileImageUrl", MemberType.PRE);
		when(memberJoinManager.join("oAuth2Token")).thenReturn(joinResponse);

		//when
		MemberLoginResponse memberLoginResponse = memberLoginManager.login("oAuth2Token");
		MemberType expectedMemberType = memberLoginResponse.getMemberType();
		String accessToken = memberLoginResponse.getAccessToken();
		String refreshToken = memberLoginResponse.getRefreshToken();

		//then
		log.info("accessToken: {}", accessToken);
		log.info("refreshToken: {}", refreshToken);
		Assertions.assertThat(expectedMemberType).isEqualTo(MemberType.PRE);
	}
}
