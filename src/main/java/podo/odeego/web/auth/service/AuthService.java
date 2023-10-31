package podo.odeego.web.auth.service;

import static podo.odeego.global.error.ErrorCode.*;

import org.springframework.stereotype.Service;

import podo.odeego.domain.refreshtoken.dto.RefreshTokenResponse;
import podo.odeego.domain.refreshtoken.exception.RefreshTokenNotFoundException;
import podo.odeego.domain.refreshtoken.exception.WrongRefreshTokenException;
import podo.odeego.domain.refreshtoken.service.RefreshTokenService;
import podo.odeego.global.error.exception.AuthenticationException;
import podo.odeego.web.auth.JwtProvider;
import podo.odeego.web.auth.dto.CustomLoginRequest;
import podo.odeego.web.auth.dto.JoinCustomAccountRequest;
import podo.odeego.web.auth.dto.LoginMemberInfoResponse;
import podo.odeego.web.auth.dto.LoginResponse;
import podo.odeego.web.auth.dto.ReissueResponse;

@Service
public class AuthService {

	private final RefreshTokenService refreshTokenService;
	private final JwtProvider jwtProvider;
	private final OAuth2Service oAuth2Service;
	private final CustomAuthService customAuthService;

	public AuthService(
		RefreshTokenService refreshTokenService,
		JwtProvider jwtProvider,
		OAuth2Service oAuth2Service,
		CustomAuthService customAuthService
	) {
		this.refreshTokenService = refreshTokenService;
		this.jwtProvider = jwtProvider;
		this.oAuth2Service = oAuth2Service;
		this.customAuthService = customAuthService;
	}

	public void join(JoinCustomAccountRequest joinRequest) {
		customAuthService.join(joinRequest);
	}

	public LoginResponse socialLogin(String oAuth2Token) {
		LoginMemberInfoResponse LoginMemberInfo = oAuth2Service.login(oAuth2Token);

		TokenResponse tokenResponse = generateToken(LoginMemberInfo.memberId());
		return LoginResponse.of(
			tokenResponse.accessToken,
			tokenResponse.refreshToken,
			LoginMemberInfo
		);
	}

	public LoginResponse customLogin(CustomLoginRequest loginRequest) {
		LoginMemberInfoResponse LoginMemberInfo = customAuthService.login(
			loginRequest.getUsername(),
			loginRequest.getPassword()
		);

		TokenResponse tokenResponse = generateToken(LoginMemberInfo.memberId());
		return LoginResponse.of(
			tokenResponse.accessToken,
			tokenResponse.refreshToken,
			LoginMemberInfo
		);
	}

	private TokenResponse generateToken(Long memberId) {
		String accessToken = jwtProvider.generateAccessToken(memberId);
		RefreshTokenResponse refreshToken = refreshTokenService.create(memberId);

		return new TokenResponse(accessToken, refreshToken.token());
	}

	public ReissueResponse reissue(String bearerToken, String oldRefreshToken) {
		String oldAccessToken = jwtProvider.resolveToken(bearerToken);
		Long memberId = jwtProvider.extractMemberIdFromExpiredJwt(oldAccessToken);

		RefreshTokenResponse refreshTokenResponse;
		try {
			refreshTokenResponse = refreshTokenService.rotate(memberId, oldRefreshToken);
		} catch (RefreshTokenNotFoundException | WrongRefreshTokenException e) {
			throw new AuthenticationException(
				"Reissue Failed. memberId: %d, RefreshToken: %s".formatted(memberId, oldRefreshToken), REISSUE_FAILED);
		}

		String accessToken = jwtProvider.generateAccessToken(memberId);
		return new ReissueResponse(accessToken, refreshTokenResponse.token());
	}

	private static class TokenResponse {
		String accessToken;
		String refreshToken;

		public TokenResponse(String accessToken, String refreshToken) {
			this.accessToken = accessToken;
			this.refreshToken = refreshToken;
		}

		public String accessToken() {
			return accessToken;
		}

		public String refreshToken() {
			return refreshToken;
		}
	}
}
