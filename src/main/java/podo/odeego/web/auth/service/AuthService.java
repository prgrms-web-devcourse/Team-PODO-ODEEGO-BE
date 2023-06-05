package podo.odeego.web.auth.service;

import org.springframework.stereotype.Service;

import podo.odeego.domain.auth.entity.RefreshToken;
import podo.odeego.domain.auth.service.RefreshTokenService;
import podo.odeego.web.auth.JwtProvider;
import podo.odeego.web.auth.dto.ReissueResponse;

@Service
public class AuthService {

	private final RefreshTokenService refreshTokenService;

	private final JwtProvider jwtProvider;

	public AuthService(RefreshTokenService refreshTokenService, JwtProvider jwtProvider) {
		this.refreshTokenService = refreshTokenService;
		this.jwtProvider = jwtProvider;
	}

	public ReissueResponse reissue(String refreshToken) {
		RefreshToken foundToken = refreshTokenService.findById(refreshToken);

		String accessToken = jwtProvider.generateAccessToken(foundToken.memberId());
		return new ReissueResponse(accessToken, refreshToken);
	}
}
