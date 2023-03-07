package podo.odeego.web.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	private static final String ID_KEY = "memberId";
	private static final String ROLE_KEY = "role";

	private final Logger log = LoggerFactory.getLogger(getClass());

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
			.claim(ROLE_KEY, "ROLE_MEMBER")
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
			.claim(ROLE_KEY, "ROLE_MEMBER")
			.compact();
	}

	private Claims parseClaims(String accessToken) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(accessToken)
			.getBody();
	}

	public Long getMemberId(String accessToken) {
		Claims claims = parseClaims(accessToken);
		return Long.parseLong(
			String.valueOf(claims.get(ID_KEY))
		);
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT Token", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty.", e);
		}
		return false;
	}
}
