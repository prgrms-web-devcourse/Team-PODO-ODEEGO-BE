package podo.odeego.web.api.auth.dto;

public record OAuth2GetTokenResponse(
	String token_type,
	String access_token,
	int expires_tn,
	String refresh_token,
	int refresh_token_expires_in,
	String scope
) {
	@Override
	public String toString() {
		return "OAuth2GetTokenResponse{" +
			"token_type='" + token_type + '\'' +
			", access_token='" + access_token + '\'' +
			", expires_tn=" + expires_tn +
			", refresh_token='" + refresh_token + '\'' +
			", refresh_token_expires_in=" + refresh_token_expires_in +
			", scope='" + scope + '\'' +
			'}';
	}
}
