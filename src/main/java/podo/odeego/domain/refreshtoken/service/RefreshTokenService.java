package podo.odeego.domain.refreshtoken.service;

import org.springframework.stereotype.Service;

import podo.odeego.domain.refreshtoken.entity.LegacyDtoRefreshToken;
import podo.odeego.domain.refreshtoken.repository.RefreshTokenRepository;
import podo.odeego.web.auth.exception.WrongRefreshTokenException;

@Service
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;

	public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
	}

	public String create(Long memberId) {
		return refreshTokenRepository.save(memberId);
	}

	// public String create(Long memberId) {
	// 	LegacyDtoRefreshToken legacyDtoRefreshToken = LegacyDtoRefreshToken.randomUUID(memberId);
	// 	refreshTokenRepository.save(legacyDtoRefreshToken);
	// 	return legacyDtoRefreshToken.token();
	// }

	//TODO: 컴파일 에러 방지
	public LegacyDtoRefreshToken findById(String refreshToken) {
		return refreshTokenRepository.findById()
			.orElseThrow(() -> new WrongRefreshTokenException("Wrong RefreshToken: %s".formatted(refreshToken)));
	}

	// public LegacyDtoRefreshToken findById(String refreshToken) {
	// 	return refreshTokenRepository.findById(refreshToken)
	// 		.orElseThrow(() -> new WrongRefreshTokenException("Wrong RefreshToken: %s".formatted(refreshToken)));
	// }
}
