package podo.odeego.web.auth;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import podo.odeego.domain.member.dto.MemberJoinResponse;
import podo.odeego.domain.member.entity.MemberType;
import podo.odeego.domain.refreshtoken.service.RefreshTokenService;
import podo.odeego.web.api.auth.dto.MemberLoginResponse;

@ExtendWith(MockitoExtension.class)
class MemberLoginManagerTest {

	@InjectMocks
	MemberLoginManager memberLoginManager;

	@Mock
	MemberJoinManager memberJoinManager;

	@Mock
	RefreshTokenService refreshTokenService;

	@Mock
	JwtProvider jwtProvider;

	@DisplayName("MemberLoginResponse의 login 기능을 통해 OAuth2 기반의 로그인이 진행되고, accessToken과 RefreshToken을 반환받습니다.")
	@Test
	public void login() {
		//given
		MemberJoinResponse joinResponse = new MemberJoinResponse(1L, "profileImageUrl", MemberType.PRE);
		doReturn(joinResponse).when(memberJoinManager)
			.join("oAuth2Token");

		String accessToken = "accessToken";
		doReturn(accessToken).when(jwtProvider)
			.generateAccessToken(joinResponse.id());

		String refreshToken = "refreshToken";
		doReturn(refreshToken).when(refreshTokenService)
			.create(joinResponse.id());

		//when
		MemberLoginResponse loginResponse = memberLoginManager.login("oAuth2Token");

		//then
		assertThat(loginResponse.getAccessToken()).isEqualTo("accessToken");
		assertThat(loginResponse.getRefreshToken()).isEqualTo("refreshToken");
	}
}