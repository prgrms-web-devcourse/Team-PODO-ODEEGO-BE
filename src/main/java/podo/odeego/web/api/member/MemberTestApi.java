package podo.odeego.web.api.member;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import podo.odeego.domain.member.dto.MemberJoinResponse;
import podo.odeego.domain.member.service.MemberService;

@RestController
public class MemberTestApi {

	private final MemberService memberService;

	public MemberTestApi(MemberService memberService) {
		this.memberService = memberService;
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
}
