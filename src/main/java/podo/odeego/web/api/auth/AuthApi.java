package podo.odeego.web.api.auth;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import podo.odeego.web.api.auth.dto.MemberLoginResponse;
import podo.odeego.web.security.service.AuthorizationComponent;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthApi {

	private static final String AUTHORIZATION = "Authorization";

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final AuthorizationComponent authorizationComponent;

	public AuthApi(AuthorizationComponent authorizationComponent) {
		this.authorizationComponent = authorizationComponent;
	}

	@PostMapping("/user/me")
	private ResponseEntity<MemberLoginResponse> getMemberInfo(HttpServletRequest request) {
		log.info("AuthApi.getMemberInfo() called");
		String oAuth2Token = request.getHeader(AUTHORIZATION);
		return ResponseEntity.ok(authorizationComponent.getMemberInfo(oAuth2Token));
	}
}
