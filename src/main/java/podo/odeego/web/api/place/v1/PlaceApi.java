package podo.odeego.web.api.place.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import podo.odeego.domain.place.domain.PlaceCategory;
import podo.odeego.domain.place.dto.PlaceResponses;
import podo.odeego.domain.place.service.PlaceQueryService;
import podo.odeego.domain.station.dto.StationInfo;

@RestController
@RequestMapping("/api/v1/places")
public class PlaceApi {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final PlaceQueryService placeQueryService;

	public PlaceApi(@Qualifier("naverPlaceQueryService") PlaceQueryService placeQueryService) {
		this.placeQueryService = placeQueryService;
	}

	@GetMapping
	public ResponseEntity<PlaceResponses> getAll(
		@RequestParam(name = "station-name") String stationName,
		@RequestParam String address,
		@RequestParam(required = false) PlaceCategory category
	) {
		PlaceResponses response = placeQueryService.getAll(new StationInfo(stationName, address), category);
		return ResponseEntity.ok(response);
	}
}