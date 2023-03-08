package podo.odeego.web.api.group.v1;

import java.net.URI;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import podo.odeego.domain.group.dto.HostStationModifyRequest;
import podo.odeego.domain.group.dto.request.GroupCreateRequest;
import podo.odeego.domain.group.dto.response.GroupResponse;
import podo.odeego.domain.group.dto.response.GroupResponses;
import podo.odeego.domain.group.service.GroupCreateService;
import podo.odeego.domain.group.service.GroupMemberAddService;
import podo.odeego.domain.group.service.GroupMemberModifyService;
import podo.odeego.domain.group.service.GroupQueryService;
import podo.odeego.domain.group.service.GroupRemoveService;
import podo.odeego.domain.midpoint.dto.StartSubmitRequest;
import podo.odeego.web.auth.config.LoginMember;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupApi {

	private static final String GROUP_LOCATION_PATH = "/api/v1/groups/{groupId}";

	private final GroupCreateService createService;
	private final GroupQueryService queryService;
	private final GroupRemoveService removeService;
	private final GroupMemberAddService addService;
	private final GroupMemberModifyService modifyService;

	public GroupApi(
		GroupCreateService createService,
		GroupQueryService queryService,
		GroupRemoveService removeService,
		GroupMemberAddService addService,
		GroupMemberModifyService modifyService) {
		this.createService = createService;
		this.queryService = queryService;
		this.removeService = removeService;
		this.addService = addService;
		this.modifyService = modifyService;
	}

	@PostMapping
	public ResponseEntity<Void> create(
		@LoginMember Long memberId,
		@RequestBody @Valid GroupCreateRequest createRequest
	) {
		UUID groupId = createService.create(memberId, createRequest);

		URI uri = UriComponentsBuilder.fromPath(GROUP_LOCATION_PATH)
			.buildAndExpand(groupId)
			.toUri();

		return ResponseEntity.created(uri)
			.build();
	}

	@GetMapping("/{groupId}")
	public ResponseEntity<GroupResponse> getOne(
		@LoginMember Long memberId,
		@PathVariable UUID groupId
	) {
		return ResponseEntity.ok()
			.body(queryService.getOne(groupId));
	}

	@GetMapping
	public ResponseEntity<GroupResponses> getAll(
		@LoginMember Long memberId
	) {
		return ResponseEntity.ok()
			.body(queryService.getAll(memberId));
	}

	@DeleteMapping("/{groupId}")
	public ResponseEntity<GroupResponse> remove(
		@LoginMember Long memberId,
		@PathVariable UUID groupId
	) {
		removeService.remove(groupId);
		return ResponseEntity.ok()
			.build();
	}

	@PostMapping("/{group-id}/group-members")
	public ResponseEntity<Void> submit(
		@LoginMember Long memberId,
		@PathVariable(value = "group-id") UUID groupId,
		@RequestBody @Valid StartSubmitRequest startSubmitRequest
	) {
		addService.add(groupId, memberId, startSubmitRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PatchMapping("/{group-id}/host")
	public ResponseEntity<Void> defineHostStation(
		@LoginMember Long hostId,
		@PathVariable(value = "group-id") UUID groupId,
		@RequestBody @Valid HostStationModifyRequest hostStationDefineRequest
	) {
		modifyService.define(groupId, hostId, hostStationDefineRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
