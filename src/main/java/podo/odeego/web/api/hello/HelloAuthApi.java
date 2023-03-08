package podo.odeego.web.api.hello;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import podo.odeego.web.auth.jwt.JwtProvider;

@RestController
@RequestMapping("/api/hello/auth")
public class HelloAuthApi {

	private final JwtProvider jwtProvider;

	public HelloAuthApi(JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}

	@PostMapping("/login")
	public String login(@RequestParam Long memberId) {
		return jwtProvider.generateToken(memberId).accessToken();
	}
}
