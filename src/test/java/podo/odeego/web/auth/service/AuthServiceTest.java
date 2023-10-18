package podo.odeego.web.auth.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import podo.odeego.domain.member.entity.MemberType;
import podo.odeego.domain.refreshtoken.dto.RefreshTokenResponse;
import podo.odeego.domain.refreshtoken.exception.RefreshTokenNotFoundException;
import podo.odeego.domain.refreshtoken.exception.WrongRefreshTokenException;
import podo.odeego.domain.refreshtoken.service.RefreshTokenService;
import podo.odeego.global.error.exception.AuthenticationException;
import podo.odeego.web.auth.JwtProvider;
import podo.odeego.web.auth.dto.LoginMemberInfoResponse;
import podo.odeego.web.auth.dto.LoginResponse;
import podo.odeego.web.auth.dto.ReissueRequest;
import podo.odeego.web.auth.dto.ReissueResponse;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@InjectMocks
	AuthService authService;

	@Mock
	RefreshTokenService refreshTokenService;

	@Mock
	JwtProvider jwtProvider;

	@Mock
	OAuth2Service oAuth2Service;

	@DisplayName("OAuth2 기반의 소셜 로그인을 진행하면 사용자 프로필 및 access token, refresh token을 발급받을 수 있습니다.")
	@Test
	public void login() {
		//given
		LoginMemberInfoResponse loginMemberInfo = new LoginMemberInfoResponse(1L, "profileImageUrl", MemberType.PRE);
		doReturn(loginMemberInfo).when(oAuth2Service)
			.login("oAuth2Token");

		String accessToken = "accessToken";
		doReturn(accessToken).when(jwtProvider)
			.generateAccessToken(loginMemberInfo.memberId());

		doReturn(new RefreshTokenResponse("refreshToken")).when(refreshTokenService)
			.create(loginMemberInfo.memberId());

		//when
		LoginResponse loginResponse = authService.socialLogin("oAuth2Token");

		//then
		assertThat(loginResponse.getAccessToken()).isEqualTo("accessToken");
		assertThat(loginResponse.getRefreshToken()).isEqualTo("refreshToken");
		assertThat(loginResponse.getMemberType()).isEqualTo(MemberType.PRE);
		assertThat(loginResponse.getProfileImageUrl()).isEqualTo("profileImageUrl");
	}

	@DisplayName("reissue() 메서드 호출을 통해 Refresh Token과 Access Token을 재발급 받을 수 있습니다.")
	@Test
	public void reissue() {
		//given
		doReturn(1L).when(jwtProvider)
			.extractMemberIdFromExpiredJwt("accessToken");

		doReturn(new RefreshTokenResponse("newRefreshToken")).when(refreshTokenService)
			.rotate(1L, "refreshToken");

		String accessToken = "accessToken";
		doReturn(accessToken).when(jwtProvider)
			.generateAccessToken(1L);
		ReissueRequest reissueRequest = new ReissueRequest(accessToken);

		//when
		ReissueResponse expectedResponse = authService.reissue(reissueRequest, "refreshToken");
		String expectedAccessToken = expectedResponse.accessToken();
		String expectedRefreshToken = expectedResponse.refreshToken();

		//then
		Assertions.assertThat(expectedAccessToken).isEqualTo("accessToken");
		Assertions.assertThat(expectedRefreshToken).isEqualTo("newRefreshToken");
	}

	@Test
	@DisplayName("저장소에서 조회한 RefreshToken과 일치하지 않는 RefreshToken으로 재발급 받으려고 하면 재발급에 실패하고 예외가 발생합니다.")
	void reissueFailByRefreshToken() {
		//given
		String wrongRefreshToken = "wrongRefreshToken";
		doReturn(1L).when(jwtProvider)
			.extractMemberIdFromExpiredJwt("accessToken");

		doThrow(WrongRefreshTokenException.class).when(refreshTokenService)
			.rotate(1L, wrongRefreshToken);

		String accessToken = "accessToken";
		ReissueRequest reissueRequest = new ReissueRequest(accessToken);

		//when & then
		assertThatThrownBy(() -> authService.reissue(reissueRequest, wrongRefreshToken))
			.isInstanceOf(AuthenticationException.class);
	}

	@Test
	@DisplayName("RefreshToken 저장소에 존재하지 않는 memberId로 재발급 받으려고 하면 재발급에 실패하고 예외가 발생합니다.")
	void reissueFailByMemberId() {
		//given
		Long wrongMemberId = 1L;
		doReturn(wrongMemberId).when(jwtProvider)
			.extractMemberIdFromExpiredJwt("accessToken");

		doThrow(RefreshTokenNotFoundException.class).when(refreshTokenService)
			.rotate(wrongMemberId, "notFoundRefreshToken");

		String accessToken = "accessToken";
		ReissueRequest reissueRequest = new ReissueRequest(accessToken);

		//when & then
		assertThatThrownBy(() -> authService.reissue(reissueRequest, "notFoundRefreshToken"))
			.isInstanceOf(AuthenticationException.class);
	}
}