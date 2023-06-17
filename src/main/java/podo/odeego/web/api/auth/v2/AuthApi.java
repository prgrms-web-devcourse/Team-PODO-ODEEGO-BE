package podo.odeego.web.api.auth.v2;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import podo.odeego.web.auth.dto.LoginResponse;
import podo.odeego.web.auth.service.AuthService;

@RestController
@RequestMapping("/api/v2/auth")
public class AuthApi {

	private final AuthService authService;

	public AuthApi(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login/oauth2")
	public ResponseEntity<LoginResponse> login(HttpServletRequest request) {
		String oAuth2Token = request.getHeader(HttpHeaders.AUTHORIZATION);
		return ResponseEntity.ok(authService.socialLogin(oAuth2Token));
	}
}
