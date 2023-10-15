package podo.odeego.domain.refreshtoken.entity;

import java.io.Serializable;

public class RefreshToken implements Serializable {

	private final String token;
	private final boolean isValid;

	public RefreshToken(String token, boolean isValid) {
		this.token = token;
		this.isValid = isValid;
	}

	public String getToken() {
		return token;
	}

	public boolean isValid() {
		return isValid;
	}
}
