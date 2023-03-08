package podo.odeego.web.api.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloIndexApi {

	@GetMapping("/api/hello/index")
	public String index() {
		return "index";
	}

	@GetMapping("/api/hello/auth/login")
	public String login() {
		return "auth/login";
	}
}
