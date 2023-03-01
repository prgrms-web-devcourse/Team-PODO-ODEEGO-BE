package podo.odeego.web.security.jwt;

public record JwtAuthenticationPrincipal(
	String token,
	Long memberId
) {
}
