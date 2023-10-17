package podo.odeego.domain.refreshtoken.entity;

import org.springframework.data.annotation.Id;

public class RefreshToken {

	@Id
	private final Long memberId;
	private String token;

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

	public boolean isTokenEqualsTo(String oldToken) {
		return this.token.equals(oldToken);
	}

	public void changeNewToken(String newToken) {
		this.token = newToken;
	}
}
