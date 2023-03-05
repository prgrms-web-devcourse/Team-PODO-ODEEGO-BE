package podo.odeego.web.api.place.v1;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import podo.odeego.domain.place.dto.PlaceQueryResponses;
import podo.odeego.domain.place.entity.PlaceCategory;
import podo.odeego.domain.place.service.PlaceQueryService;

@RestController
@RequestMapping("/api/v1/places")
public class PlaceApi {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final PlaceQueryService placeQueryService;

	public PlaceApi(@Qualifier("naverPlaceQueryService") PlaceQueryService placeQueryService) {
		this.placeQueryService = placeQueryService;
	}

	@GetMapping
	public ResponseEntity<PlaceQueryResponses> getAll(
		@RequestParam(name = "station-name") String stationName,
		@RequestParam Optional<PlaceCategory> category
	) {
		PlaceQueryResponses response = placeQueryService.getAll(stationName, category.orElse(PlaceCategory.ALL));
		return ResponseEntity.ok(response);
	}
}
