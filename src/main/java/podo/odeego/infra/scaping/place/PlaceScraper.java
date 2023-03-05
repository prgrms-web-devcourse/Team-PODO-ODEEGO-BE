package podo.odeego.infra.scaping.place;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import podo.odeego.domain.place.dto.PlaceQueryResponse;
import podo.odeego.domain.place.dto.PlaceQueryResponses;
import podo.odeego.domain.place.entity.PlaceCategory;
import podo.odeego.domain.place.service.PlaceCreateService;
import podo.odeego.domain.place.service.PlaceQueryService;
import podo.odeego.domain.station.dto.StationNameQueryDto;
import podo.odeego.domain.station.service.StationFindService;

@Component
public class PlaceScraper {

	private final StationFindService stationFindService;
	private final PlaceCreateService placeCreateService;
	private final PlaceQueryService naverPlaceQueryService;

	public PlaceScraper(
		StationFindService stationFindService,
		PlaceCreateService placeCreateService,
		@Qualifier("naverPlaceQueryService") PlaceQueryService naverPlaceQueryService
	) {
		this.stationFindService = stationFindService;
		this.placeCreateService = placeCreateService;
		this.naverPlaceQueryService = naverPlaceQueryService;
	}

	public void scrapRestaurantsAndCafesNearByStation() {
		// 역 가져와서,
		List<StationNameQueryDto> queries = stationFindService.getAllStationName();

		// 쿼리 날리고
		List<PlaceQueryResponse> placeSimpleResponses = queries.stream()
			.map(stationName -> naverPlaceQueryService.getAll(stationName.name(), PlaceCategory.ALL))
			.map(PlaceQueryResponses::getPlaces)
			.flatMap(List::stream)
			.map(placeSimpleResponse -> placeSimpleResponse.)
			.toList();

		// PlaceSimpleResponse & stationName -> PlaceCreateRequest

		// List<Place> -> DB

	}
}
