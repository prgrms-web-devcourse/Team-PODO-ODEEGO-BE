package podo.odeego.web.api.hello;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import podo.odeego.domain.member.dto.MemberJoinRes;
import podo.odeego.domain.member.service.MemberService;
import podo.odeego.web.security.jwt.JwtProvider;

@RestController
@RequestMapping("/api/hello/auth")
public class HelloAuthApi {

	private final MemberService memberService;
	private final JwtProvider jwtProvider;

	public HelloAuthApi(MemberService memberService, JwtProvider jwtProvider) {
		this.memberService = memberService;
		this.jwtProvider = jwtProvider;
	}

	@PostMapping("/join")
	public Long join() {
		MemberJoinRes join = memberService.join("helloProvider", "helloProviderId");
		return join.id();
	}

	@PostMapping("/login")
	public String login(@RequestParam Long memberId) {
		return jwtProvider.generateAccessToken(memberId);
	}
}
