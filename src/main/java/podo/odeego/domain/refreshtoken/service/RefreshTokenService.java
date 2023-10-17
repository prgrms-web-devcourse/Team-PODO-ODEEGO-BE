package podo.odeego.domain.refreshtoken.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import podo.odeego.domain.refreshtoken.dto.RefreshTokenResponse;
import podo.odeego.domain.refreshtoken.entity.RefreshToken;
import podo.odeego.domain.refreshtoken.repository.RefreshTokenRepository;
import podo.odeego.web.auth.exception.WrongRefreshTokenException;

@Service
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;

	public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
	}

	public RefreshTokenResponse create(Long memberId) {
		RefreshToken refreshToken = new RefreshToken(memberId, UUID.randomUUID().toString());
		refreshTokenRepository.save(refreshToken);
		return new RefreshTokenResponse(refreshToken.token());
	}

	//TODO: 컴파일 에러 방지
	public RefreshToken findById(String refreshToken) {
		return refreshTokenRepository.findById()
			.orElseThrow(() -> new WrongRefreshTokenException("Wrong RefreshToken: %s".formatted(refreshToken)));
	}

	// public Long findMemberIdByRefreshToken(String token) {
	// 	RefreshToken refreshToken = new RefreshToken(token, true);
	// 	return refreshTokenRepository.findMemberIdByRefreshToken(refreshToken)
	// 		.orElseThrow(() -> new WrongRefreshTokenException("Wrong RefreshToken: %s".formatted(refreshToken)));
	// }
	//
	// public String rotate(String oldRefreshToken, Long memberId) {
	// 	refreshTokenRepository.updateRefreshToken(new RefreshToken(oldRefreshToken, true),
	// 		new RefreshToken(oldRefreshToken, false));
	//
	// 	RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), true);
	// 	refreshTokenRepository.save(refreshToken, memberId);
	// 	return refreshToken.getToken();
	// }
}
