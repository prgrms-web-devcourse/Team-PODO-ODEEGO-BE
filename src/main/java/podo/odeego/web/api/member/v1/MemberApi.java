package podo.odeego.web.api.member.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import podo.odeego.domain.member.dto.MemberDefaultStationGetResponse;
import podo.odeego.domain.member.dto.MemberSignUpRequest;
import podo.odeego.domain.member.service.MemberFindService;
import podo.odeego.domain.member.service.MemberService;
import podo.odeego.web.security.jwt.JwtAuthenticationPrincipal;

@RestController
@RequestMapping("/api/v1/members")
public class MemberApi {

	private final MemberService memberService;
	private final MemberFindService memberFindService;

	public MemberApi(MemberService memberService, MemberFindService memberFindService) {
		this.memberService = memberService;
		this.memberFindService = memberFindService;
	}

	@GetMapping("/default-station")
	public ResponseEntity<MemberDefaultStationGetResponse> getDefaultStation(@RequestParam("memberId") Long memberId) {
		return ResponseEntity.ok(memberFindService.findDefaultStation(memberId));
	}

	@PatchMapping("/sign-up")
	public ResponseEntity<Void> signUp(
		@AuthenticationPrincipal JwtAuthenticationPrincipal principal,
		@RequestBody MemberSignUpRequest request
	) {
		memberService.signUp(principal.memberId(), request);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/leave")
	public ResponseEntity<Void> leave(
		@AuthenticationPrincipal JwtAuthenticationPrincipal principal
	) {
		memberService.leave(principal.memberId());
		return ResponseEntity.ok().build();
	}
}
