package podo.odeego.web.api.place.v1;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import podo.odeego.domain.place.dto.PlaceQueryResponse;
import podo.odeego.domain.place.entity.PlaceCategory;
import podo.odeego.domain.place.service.PlaceQueryService;

@RestController
@RequestMapping("/api/v1/places")
public class PlaceApi {

	private final PlaceQueryService placeQueryService;

	public PlaceApi(@Qualifier("placeQueryServiceImpl") PlaceQueryService placeQueryService) {
		this.placeQueryService = placeQueryService;
	}

	@GetMapping
	public ResponseEntity<Page<PlaceQueryResponse>> getAll(
		@RequestParam(name = "station-name") String stationName,
		@RequestParam Optional<PlaceCategory> category,
		@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
	) {
		Page<PlaceQueryResponse> response = category
			.map(placeCategory -> placeQueryService.getAll(stationName, placeCategory, pageable))
			.orElseGet(() -> placeQueryService.getAll(stationName, pageable));

		return ResponseEntity.ok(response);
	}
}
