package podo.odeego.domain.refreshtoken.dto;

public class RefreshTokenResponse {
	private final String token;

	public RefreshTokenResponse(String token) {
		this.token = token;
	}

	public String token() {
		return token;
	}
}
