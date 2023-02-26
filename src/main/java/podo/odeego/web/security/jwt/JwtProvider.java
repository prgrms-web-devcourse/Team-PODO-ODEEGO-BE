package podo.odeego.web.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	private static final String ID_KEY = "memberId";

	private final long accessTokenExpirationMillis;
	private final long refreshTokenExpirationMillis;
	private final SecretKey key;

	public JwtProvider(
		@Value("${jwt.secret}") String secretString,
		@Value("${jwt.expiration.access-token}") long accessTokenExpirationMillis,
		@Value("${jwt.expiration.refresh-token}") long refreshTokenExpirationMillis
	) {
		this.key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
		this.accessTokenExpirationMillis = accessTokenExpirationMillis;
		this.refreshTokenExpirationMillis = refreshTokenExpirationMillis;
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

	public String generateRefreshToken(Long memberId) {
		long now = (new Date()).getTime();

		Date expiresIn = new Date(now + refreshTokenExpirationMillis);
		return Jwts.builder()
			.claim(ID_KEY, memberId)
			.setExpiration(expiresIn)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}
}
