package podo.odeego.domain.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import podo.odeego.domain.auth.entity.RefreshToken;
import podo.odeego.domain.auth.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;

	public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
	}

	public String save(Long memberId) {
		RefreshToken refreshToken = RefreshToken.randomUUID(memberId);
		refreshTokenRepository.save(refreshToken);
		return refreshToken.token();
	}

	public Optional<RefreshToken> findById(String refreshToken) { // DTO 고민
		return refreshTokenRepository.findById(refreshToken);
	}
}
