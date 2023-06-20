package podo.odeego.web.auth.dto;

public class JoinCustomAccountRequest {
	private String username;
	private String password;
	private String profileImageUrl;

	private JoinCustomAccountRequest() {
	}

	public JoinCustomAccountRequest(String username, String password, String profileImageUrl) {
		this.username = username;
		this.password = password;
		this.profileImageUrl = profileImageUrl;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}
}
