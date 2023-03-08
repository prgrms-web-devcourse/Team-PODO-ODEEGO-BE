package podo.odeego.web.api.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/hello/test-with-jwt")
public class HelloTestWithJwtApi {

	@GetMapping
	public String loadTestWithJwtPage() {
		return "testWithJwt";
	}

	@GetMapping("/login")
	public String login() {
		return "auth/login";
	}
}
