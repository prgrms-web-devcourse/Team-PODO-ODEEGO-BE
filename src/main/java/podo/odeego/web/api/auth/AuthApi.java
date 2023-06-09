package podo.odeego.web.api.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import podo.odeego.web.api.auth.dto.MemberLoginResponse;
import podo.odeego.web.auth.MemberLoginManager;
import podo.odeego.web.auth.dto.ReissueResponse;
import podo.odeego.web.auth.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthApi {

	private static final String AUTHORIZATION = "Authorization";

	private final MemberLoginManager memberLoginManager;

	private final AuthService authService;

	public AuthApi(MemberLoginManager memberLoginManager, AuthService authService) {
		this.memberLoginManager = memberLoginManager;
		this.authService = authService;
	}

	@PostMapping("/user/me")
	public ResponseEntity<MemberLoginResponse> login(HttpServletRequest request) {
		String oAuth2Token = request.getHeader(AUTHORIZATION);
		return ResponseEntity.ok(memberLoginManager.login(oAuth2Token));
	}

	@PostMapping("/reissue")
	public ResponseEntity<ReissueResponse> reissue(@CookieValue("refreshToken") String refreshToken) {
		return ResponseEntity.ok(authService.reissue(refreshToken));
	}
}
