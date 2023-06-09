package podo.odeego.web.auth.dto;

public record ReissueResponse(
	String accessToken,
	String refreshToken
) {
}
