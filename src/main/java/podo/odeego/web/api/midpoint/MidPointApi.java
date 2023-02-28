package podo.odeego.web.api.midpoint;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import podo.odeego.domain.group.service.GroupMemberAddService;
import podo.odeego.domain.midpoint.dto.MidPointSearchRequest;
import podo.odeego.domain.midpoint.dto.MidPointSearchResponse;
import podo.odeego.domain.midpoint.dto.StartSubmitRequest;
import podo.odeego.domain.midpoint.service.MidPointQueryService;

@RestController
@RequestMapping("/api/v1/mid-points")
public class MidPointApi {

	private final MidPointQueryService midPointQueryService;
	private final GroupMemberAddService groupMemberAddService;

	public MidPointApi(MidPointQueryService midPointQueryService, GroupMemberAddService groupMemberAddService) {
		this.midPointQueryService = midPointQueryService;
		this.groupMemberAddService = groupMemberAddService;
	}

	@PostMapping("/search")
	public ResponseEntity<MidPointSearchResponse> search(
		@RequestBody @Valid MidPointSearchRequest midPointSearchRequest) {
		return ResponseEntity.ok(midPointQueryService.search(midPointSearchRequest));
	}

	@PatchMapping("/starts")
	public ResponseEntity<Void> submit(@RequestParam(value = "memberId") Long memberId,
		@RequestBody @Valid StartSubmitRequest startSubmitRequest) {
		groupMemberAddService.add(memberId, startSubmitRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
