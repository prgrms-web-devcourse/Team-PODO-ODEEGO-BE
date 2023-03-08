package podo.odeego.web.auth.jwt.dto;

public record GenerateTokenResponse (
	String accessToken,
	String refreshToken
) {
}
