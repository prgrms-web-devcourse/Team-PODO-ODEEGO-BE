package podo.odeego.web.auth.service;

import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import podo.odeego.domain.refreshtoken.entity.RefreshToken;
import podo.odeego.domain.refreshtoken.service.RefreshTokenService;
import podo.odeego.web.auth.JwtProvider;
import podo.odeego.web.auth.dto.ReissueResponse;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@InjectMocks
	AuthService authService;

	@Mock
	RefreshTokenService refreshTokenService;

	@Mock
	JwtProvider jwtProvider;

	@DisplayName("RefreshToken Storage에 Refresh Token이 존재한다면 Access Token을 재발급 받을 수 있습니다.")
	@Test
	public void reissue() {
		//given
		RefreshToken refreshToken = RefreshToken.of("refreshToken", 1L);
		doReturn(refreshToken).when(refreshTokenService)
			.findById("refreshToken");

		String accessToken = "accessToken";
		doReturn(accessToken).when(jwtProvider)
			.generateAccessToken(refreshToken.memberId());

		//when
		ReissueResponse expectedResponse = authService.reissue("refreshToken");
		String expectedAccessToken = expectedResponse.accessToken();
		String expectedRefreshToken = expectedResponse.refreshToken();

		//then
		Assertions.assertThat(expectedAccessToken).isEqualTo("accessToken");
		Assertions.assertThat(expectedRefreshToken).isEqualTo("refreshToken");
	}
}