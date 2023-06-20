package podo.odeego.web.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import podo.odeego.domain.refreshtoken.entity.RefreshToken;
import podo.odeego.domain.refreshtoken.service.RefreshTokenService;
import podo.odeego.web.auth.JwtProvider;
import podo.odeego.web.auth.dto.CustomLoginRequest;
import podo.odeego.web.auth.dto.JoinCustomAccountRequest;
import podo.odeego.web.auth.dto.LoginMemberInfoResponse;
import podo.odeego.web.auth.dto.LoginResponse;
import podo.odeego.web.auth.dto.ReissueResponse;

@Service
public class AuthService {

	private final Logger log = LoggerFactory.getLogger(AuthService.class);

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
		String refreshToken = refreshTokenService.create(memberId);

		return new TokenResponse(accessToken, refreshToken);
	}

	public ReissueResponse reissue(String refreshToken) {
		RefreshToken foundToken = refreshTokenService.findById(refreshToken);
		log.info("Refresh Token found: {}", foundToken);

		String accessToken = jwtProvider.generateAccessToken(foundToken.memberId());
		return new ReissueResponse(accessToken, refreshToken);
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
