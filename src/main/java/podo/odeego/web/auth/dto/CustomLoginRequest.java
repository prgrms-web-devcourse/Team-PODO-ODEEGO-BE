package podo.odeego.web.auth.dto;

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
}
