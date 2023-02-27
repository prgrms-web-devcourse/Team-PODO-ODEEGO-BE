package podo.odeego.web.api.group.v1;

import java.net.URI;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import podo.odeego.domain.group.dto.request.GroupCreateRequest;
import podo.odeego.domain.group.service.GroupCreateService;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupApi {

	private static final String GROUP_LOCATION_PATH = "/api/v1/groups{groupId}";

	private final GroupCreateService createService;

	public GroupApi(GroupCreateService createService) {
		this.createService = createService;
	}

	@PostMapping
	public ResponseEntity<Void> create(
		@RequestParam Long memberId,
		@RequestBody @Valid GroupCreateRequest createRequest
	) {
		UUID groupId = createService.create(memberId, createRequest);

		URI uri = UriComponentsBuilder.fromPath(GROUP_LOCATION_PATH)
			.buildAndExpand(groupId)
			.toUri();

		return ResponseEntity.created(uri)
			.build();
	}
}
