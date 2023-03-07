package podo.odeego.web.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	private static final String ID_KEY = "memberId";
	private static final String ROLE_KEY = "role";
	private static final String ROLES_SPLIT_REGEX = ",";

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

	public Authentication getAuthentication(String jwtToken) {
		Claims claims = parseClaims(jwtToken);
		Collection<? extends GrantedAuthority> authorities =
			Arrays.stream(claims.get(ROLE_KEY).toString().split(ROLES_SPLIT_REGEX))
				.map(SimpleGrantedAuthority::new)
				.toList();

		JwtAuthenticationPrincipal principal = new JwtAuthenticationPrincipal(
			jwtToken,
			Long.parseLong(
				String.valueOf(claims.get(ID_KEY))
			)
		);
		return JwtAuthenticationToken.authenticated(principal, "", authorities);
	}

	private Claims parseClaims(String accessToken) {
		return getJwtParser()
			.parseClaimsJws(accessToken)
			.getBody();
	}

	public boolean validateToken(String token) {
		try {
			getJwtParser().parseClaimsJws(token);
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			return false;
			// throw new InvalidJwtException("Invalid JWT: %s".formatted(token));
		} catch (io.jsonwebtoken.ExpiredJwtException e) {
			return false;
			// throw new ExpiredJwtException("Expired JWT: %s".formatted(token));
		}
		return true;
	}

	private JwtParser getJwtParser() {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build();
	}
}
