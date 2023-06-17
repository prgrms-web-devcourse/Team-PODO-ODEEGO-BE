package podo.odeego.web.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import podo.odeego.domain.refreshtoken.entity.RefreshToken;
import podo.odeego.domain.refreshtoken.service.RefreshTokenService;
import podo.odeego.web.auth.JwtProvider;
import podo.odeego.web.auth.dto.LoginResponse;
import podo.odeego.web.auth.dto.OAuth2LoginResponse;
import podo.odeego.web.auth.dto.ReissueResponse;

@Service
public class AuthService {

	private final Logger log = LoggerFactory.getLogger(AuthService.class);

	private final RefreshTokenService refreshTokenService;
	private final JwtProvider jwtProvider;
	private final OAuth2Service oAuth2Service;

	public AuthService(
		RefreshTokenService refreshTokenService,
		JwtProvider jwtProvider,
		OAuth2Service oAuth2Service
	) {
		this.refreshTokenService = refreshTokenService;
		this.jwtProvider = jwtProvider;
		this.oAuth2Service = oAuth2Service;
	}

	public LoginResponse socialLogin(String oAuth2Token) {
		OAuth2LoginResponse oAuth2LoginResponse = oAuth2Service.login(oAuth2Token);
		String accessToken = jwtProvider.generateAccessToken(oAuth2LoginResponse.id());
		String refreshToken = refreshTokenService.create(oAuth2LoginResponse.id());

		return LoginResponse.of(accessToken, refreshToken, oAuth2LoginResponse);
	}

	public ReissueResponse reissue(String refreshToken) {
		RefreshToken foundToken = refreshTokenService.findById(refreshToken);
		log.info("Refresh Token found: {}", foundToken);

		String accessToken = jwtProvider.generateAccessToken(foundToken.memberId());
		return new ReissueResponse(accessToken, refreshToken);
	}
}
