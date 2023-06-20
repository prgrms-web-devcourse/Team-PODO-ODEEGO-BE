package podo.odeego.web.api.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import podo.odeego.domain.member.service.MemberService;

@RestController
@RequestMapping("/api/test/members")
public class MemberTestApi {

	private final MemberService memberService;

	public MemberTestApi(MemberService memberService) {
		this.memberService = memberService;
	}

	@DeleteMapping("/leave")
	public ResponseEntity<Void> leave(
		@RequestParam(name = "member-id") Long memberId
	) {
		memberService.leave(memberId);
		return ResponseEntity.ok().build();
	}
}
