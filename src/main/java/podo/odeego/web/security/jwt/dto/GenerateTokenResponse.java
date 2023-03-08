package podo.odeego.web.security.jwt.dto;

public record GenerateTokenResponse (
	String accessToken,
	String refreshToken
) {
}
