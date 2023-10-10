package podo.odeego.web.auth.dto;

import java.util.Objects;

public class CustomLoginRequest {
	private String username;
	private String password;

	private CustomLoginRequest() {
	}

	public CustomLoginRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CustomLoginRequest that = (CustomLoginRequest)o;
		return Objects.equals(username, that.username) && Objects.equals(password, that.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, password);
	}
}
