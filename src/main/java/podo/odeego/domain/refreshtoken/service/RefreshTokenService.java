package podo.odeego.domain.refreshtoken.service;

import org.springframework.stereotype.Service;

import podo.odeego.domain.refreshtoken.entity.RefreshToken;
import podo.odeego.domain.refreshtoken.repository.RefreshTokenRepository;
import podo.odeego.web.auth.exception.WrongRefreshTokenException;

@Service
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;

	public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
	}

	public String create(Long memberId) {
		RefreshToken refreshToken = RefreshToken.randomUUID(memberId);
		refreshTokenRepository.save(refreshToken);
		return refreshToken.token();
	}

	public RefreshToken findById(String refreshToken) {
		return refreshTokenRepository.findById(refreshToken)
			.orElseThrow(() -> new WrongRefreshTokenException("Wrong RefreshToken: %s".formatted(refreshToken)));
	}
}
