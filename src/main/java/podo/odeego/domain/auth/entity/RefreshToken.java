package podo.odeego.domain.auth.entity;

import java.util.UUID;

import javax.persistence.Id;

public class RefreshToken {

	@Id
	private final String token;
	private final Long memberId;

	private RefreshToken(String token, Long memberId) {
		this.token = token;
		this.memberId = memberId;
	}

	public static RefreshToken randomUUID(Long memberId) {
		return new RefreshToken(UUID.randomUUID().toString(), memberId);
	}

	public static RefreshToken of(String refreshToken, Long memberId) {
		return new RefreshToken(refreshToken, memberId);
	}

	public String token() {
		return token;
	}

	public Long memberId() {
		return memberId;
	}
}
