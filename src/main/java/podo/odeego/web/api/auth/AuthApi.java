package podo.odeego.web.api.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import podo.odeego.web.api.auth.dto.MemberLoginResponse;
import podo.odeego.web.auth.MemberLoginManager;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthApi {

	private static final String AUTHORIZATION = "Authorization";

	private final MemberLoginManager memberLoginManager;

	public AuthApi(MemberLoginManager memberLoginManager) {
		this.memberLoginManager = memberLoginManager;
	}

	@PostMapping("/user/me")
	public ResponseEntity<MemberLoginResponse> login(HttpServletRequest request) {
		String oAuth2Token = request.getHeader(AUTHORIZATION);
		return ResponseEntity.ok(memberLoginManager.login(oAuth2Token));
	}
}
