package podo.odeego.domain.refreshtoken.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;

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

	@Override
	public String toString() {
		return "RefreshToken{" +
			"token='" + token + '\'' +
			", memberId=" + memberId +
			'}';
	}
}
