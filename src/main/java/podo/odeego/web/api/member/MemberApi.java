package podo.odeego.web.api.member;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import podo.odeego.domain.member.dto.MemberDefaultStationGetResponse;
import podo.odeego.domain.member.dto.MemberJoinResponse;
import podo.odeego.domain.member.dto.MemberSignUpRequest;
import podo.odeego.domain.member.service.MemberFindService;
import podo.odeego.domain.member.service.MemberService;
import podo.odeego.web.security.jwt.JwtAuthenticationPrincipal;

@RestController
public class MemberApi {

	private final MemberService memberService;
	private final MemberFindService memberFindService;

	public MemberApi(MemberService memberService, MemberFindService memberFindService) {
		this.memberService = memberService;
		this.memberFindService = memberFindService;
	}

	@PostMapping("/api/test/members")
	public ResponseEntity<MemberJoinResponse> create(
		@RequestParam String nickname
	) {
		Long joinedMemberId = memberService.join(nickname);

		URI uri = UriComponentsBuilder.fromPath("/api/test/dummy-member/{memberId}")
			.buildAndExpand(joinedMemberId)
			.toUri();

		return ResponseEntity.created(uri)
			.build();
	}

	@GetMapping("/api/v1/members/default-station")
	public ResponseEntity<MemberDefaultStationGetResponse> getDefaultStation(@RequestParam("memberId") Long memberId) {
		return ResponseEntity.ok(memberFindService.findDefaultStation(memberId));
	}

	@PatchMapping("/api/v1/members/sign-up")
	public ResponseEntity<Void> signUp(
		@AuthenticationPrincipal JwtAuthenticationPrincipal principal,
		@RequestBody MemberSignUpRequest request
	) {
		memberService.signUp(principal.memberId(), request);
		return ResponseEntity.ok().build();
	}
}
