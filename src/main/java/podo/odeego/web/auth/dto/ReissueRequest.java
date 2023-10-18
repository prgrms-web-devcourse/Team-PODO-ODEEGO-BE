package podo.odeego.web.auth.dto;

public class ReissueRequest {
	private String accessToken;

	public ReissueRequest() {
	}

	public ReissueRequest(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}
}
