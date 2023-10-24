package podo.odeego.web.api.auth.v2;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import podo.odeego.web.auth.dto.CustomLoginRequest;
import podo.odeego.web.auth.dto.JoinCustomAccountRequest;
import podo.odeego.web.auth.dto.LoginResponse;
import podo.odeego.web.auth.dto.ReissueResponse;
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
		LoginResponse loginResponse = authService.socialLogin(oAuth2Token);
		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, generateCookie("refreshToken", loginResponse.getRefreshToken()))
			.body(loginResponse);
	}

	@PostMapping("/join/custom")
	public ResponseEntity<Void> joinCustomAccount(
		@RequestBody JoinCustomAccountRequest joinRequest
	) {
		authService.join(joinRequest);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/login/custom")
	public ResponseEntity<LoginResponse> login(
		@RequestBody CustomLoginRequest loginRequest
	) {
		LoginResponse loginResponse = authService.customLogin(loginRequest);
		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, generateCookie("refreshToken", loginResponse.getRefreshToken()))
			.body(loginResponse);
	}

	@PostMapping("/reissue")
	public ResponseEntity<ReissueResponse> reissue(
		@CookieValue("refreshToken") String refreshToken,
		HttpServletRequest request
	) {
		ReissueResponse reissueResponse = authService.reissue(request.getHeader(HttpHeaders.AUTHORIZATION),
			refreshToken);
		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, generateCookie("refreshToken", reissueResponse.refreshToken()))
			.body(reissueResponse);
	}

	private String generateCookie(String name, String value) {
		return ResponseCookie.from(name, value)
			.httpOnly(true)
			.secure(true)
			.sameSite("None")
			.path("/api/v2/auth")
			.build()
			.toString();
	}
}
