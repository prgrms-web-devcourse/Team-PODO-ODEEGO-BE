package podo.odeego.domain.refreshtoken.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import podo.odeego.domain.refreshtoken.dto.RefreshTokenResponse;
import podo.odeego.domain.refreshtoken.entity.RefreshToken;
import podo.odeego.domain.refreshtoken.exception.RefreshTokenNotFoundException;
import podo.odeego.domain.refreshtoken.exception.WrongRefreshTokenException;
import podo.odeego.domain.refreshtoken.repository.RefreshTokenRepository;

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

	public RefreshTokenResponse rotate(Long memberId, String oldRefreshToken) {
		RefreshToken refreshToken = findByMemberId(memberId);

		if (!refreshToken.isTokenEqualsTo(oldRefreshToken)) {
			refreshTokenRepository.deleteByMemberId(memberId);
			throw new WrongRefreshTokenException("Wrong Refresh Token: %s".formatted(oldRefreshToken));
		}

		refreshToken.changeNewToken(UUID.randomUUID().toString());
		refreshTokenRepository.update(refreshToken);
		return new RefreshTokenResponse(refreshToken.token());
	}

	private RefreshToken findByMemberId(Long memberId) {
		return refreshTokenRepository.findByMemberId(memberId)
			.orElseThrow(
				() -> new RefreshTokenNotFoundException("RefreshToken Not Found. memberId: %d".formatted(memberId)));
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
