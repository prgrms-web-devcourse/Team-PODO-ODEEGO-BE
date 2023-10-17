package podo.odeego.domain.refreshtoken.entity;

import org.springframework.data.annotation.Id;

public class RefreshToken {

	@Id
	private final Long memberId;
	private final String token;

	public RefreshToken(Long memberId, String token) {
		this.memberId = memberId;
		this.token = token;
	}

	public Long memberId() {
		return memberId;
	}

	public String token() {
		return token;
	}
}
