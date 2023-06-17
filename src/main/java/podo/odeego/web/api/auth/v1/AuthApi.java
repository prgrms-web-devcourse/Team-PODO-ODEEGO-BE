package podo.odeego.web.api.auth.v1;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import podo.odeego.web.auth.dto.ReissueResponse;
import podo.odeego.web.auth.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthApi {

	private final AuthService authService;

	public AuthApi(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/user/me")
	public void login(HttpServletRequest request, HttpServletResponse response) throws
		IOException {
		response.setHeader(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION));
		response.sendRedirect("/api/v2/auth/login/oauth2");
	}

	@PostMapping("/reissue")
	public ResponseEntity<ReissueResponse> reissue(@CookieValue("refreshToken") String refreshToken) {
		return ResponseEntity.ok(authService.reissue(refreshToken));
	}
}
