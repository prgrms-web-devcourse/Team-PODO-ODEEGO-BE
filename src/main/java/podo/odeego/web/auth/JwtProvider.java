package podo.odeego.web.auth;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import podo.odeego.web.auth.exception.ExpiredJwtException;
import podo.odeego.web.auth.exception.InvalidJwtException;

@Component
public class JwtProvider {

	private static final String ID_KEY = "memberId";

	private final long accessTokenExpirationMillis;
	private final SecretKey key;

	public JwtProvider(
		@Value("${jwt.secret}") String key,
		@Value("${jwt.expiration.access-token}") long accessTokenExpirationMillis
	) {
		this.key = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
		this.accessTokenExpirationMillis = accessTokenExpirationMillis;
	}

	public String generateAccessToken(Long memberId) {
		long now = (new Date()).getTime();

		Date expiresIn = new Date(now + accessTokenExpirationMillis);
		return Jwts.builder()
			.claim(ID_KEY, memberId)
			.setExpiration(expiresIn)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public Long extractMemberId(String accessToken) {
		Claims claims = parseClaims(accessToken);
		return Long.parseLong(
			String.valueOf(claims.get(ID_KEY))
		);
	}

	private Claims parseClaims(String accessToken) {
		return getJwtParser()
			.parseClaimsJws(accessToken)
			.getBody();
	}

	public void validateToken(String token) {
		try {
			getJwtParser().parseClaimsJws(token);
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			throw new InvalidJwtException("Invalid JWT: %s".formatted(token));
		} catch (io.jsonwebtoken.ExpiredJwtException e) {
			throw new ExpiredJwtException("Expired JWT: %s".formatted(token));
		}
	}

	private JwtParser getJwtParser() {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build();
	}
}
