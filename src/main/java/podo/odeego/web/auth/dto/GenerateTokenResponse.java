package podo.odeego.web.auth.dto;

public record GenerateTokenResponse (
	String accessToken,
	String refreshToken
) {
}
