package podo.odeego.web.api.midpoint;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import podo.odeego.domain.midpoint.dto.MidPointSearchRequest;
import podo.odeego.domain.midpoint.dto.MidPointSearchResponse;
import podo.odeego.domain.midpoint.service.MidPointService;

@RestController
@RequestMapping("/api/v1/mid-points")
public class MidPointApi {

	private final MidPointService midPointService;

	public MidPointApi(MidPointService midPointService) {
		this.midPointService = midPointService;
	}

	@PostMapping("/search")
	public ResponseEntity<MidPointSearchResponse> search(
		@RequestBody @Valid MidPointSearchRequest midPointSearchRequest) {
		return ResponseEntity.ok(midPointService.search(midPointSearchRequest));
	}
}
