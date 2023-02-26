package podo.odeego.web.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	private static final String NICKNAME_KEY = "nickname";
	private static final String ID_KEY = "id";
	private static final String ROLE_KEY = "role";
	private static final String SECRET_STRING = "UCVDXFYGZJ3K4M5P7Q8RATBUCWEXFYH2J3K5N6P7R9SATCVDWEYGZH2J4M";
	private static final int EXPIRATION_MILLIS = 1800000;

	private final SecretKey key;

	public JwtProvider() {
		this.key = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(String nickname, Long memberId, Collection<GrantedAuthority> authorities) {
		String parsedAuthorities = authorities.stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		long now = (new Date()).getTime();

		Date expiresIn = new Date(now + EXPIRATION_MILLIS);
		return Jwts.builder()
			.claim(NICKNAME_KEY, nickname)
			.claim(ID_KEY, memberId)
			.claim(ROLE_KEY, parsedAuthorities)
			.setExpiration(expiresIn)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public String generateToken(Long memberId) {
		long now = (new Date()).getTime();

		Date expiresIn = new Date(now + EXPIRATION_MILLIS);
		return Jwts.builder()
			.claim(ID_KEY, memberId)
			.setExpiration(expiresIn)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}
}
