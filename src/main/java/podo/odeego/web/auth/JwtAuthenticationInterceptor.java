package podo.odeego.web.auth;

import static org.springframework.http.HttpHeaders.*;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import podo.odeego.web.auth.exception.TokenNotFoundException;
import podo.odeego.web.auth.exception.TokenTypeNotGrantedException;

public class JwtAuthenticationInterceptor implements HandlerInterceptor {

	public static final String MEMBER_ID = "memberId";

	private static final String OPTIONS = "OPTIONS";
	private static final String BEARER_PREFIX = "Bearer";
	private static final int SPLIT_AT = 7;

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final JwtProvider jwtProvider;

	public JwtAuthenticationInterceptor(JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (request.getMethod().equals(OPTIONS)) {
			return true;
		}
		String token = resolveToken(request);

		jwtProvider.validateToken(token);
		log.info("Verify Access Token. memberId: {}", jwtProvider.extractMemberId(token));
		request.setAttribute(MEMBER_ID, jwtProvider.extractMemberId(token));
		return true;
	}

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = Optional.ofNullable(request.getHeader(AUTHORIZATION))
			.orElseThrow(() -> new TokenNotFoundException("Jwt not found in request header."));

		if (bearerToken.startsWith(BEARER_PREFIX)) {
			return bearerToken.substring(SPLIT_AT);
		} else {
			throw new TokenTypeNotGrantedException(
				"Not granted token type. Token type must be %s".formatted(BEARER_PREFIX));
		}
	}
}
