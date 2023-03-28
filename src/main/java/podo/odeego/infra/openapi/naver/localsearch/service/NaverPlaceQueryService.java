package podo.odeego.infra.openapi.naver.localsearch.service;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.stereotype.Service;

import podo.odeego.domain.place.dto.PlaceQueryResponse;
import podo.odeego.domain.place.dto.PlaceQueryResponses;
import podo.odeego.domain.place.entity.PlaceCategory;
import podo.odeego.domain.station.service.StationFindService;
import podo.odeego.infra.openapi.naver.localsearch.client.LocalSearchClient;
import podo.odeego.infra.openapi.naver.localsearch.dto.LocalSearchQueryDto;

@Service
public class NaverPlaceQueryService {

	private final LocalSearchClient client;

	private final StationFindService stationFindService;

	public NaverPlaceQueryService(
		LocalSearchClient client,
		StationFindService stationFindService
	) {
		this.client = client;
		this.stationFindService = stationFindService;
	}

	/**
	 * @deprecated
	 * Deprecated because of limit of open-api & API Response format changing.
	 * Use podo.odeego.domain.place.service.PlaceQueryServiceImpl.java instead.
	 */
	@Deprecated
	public PlaceQueryResponses getAll(String stationName, PlaceCategory category, Pageable pageable) {
		stationFindService.verifyStationExists(stationName);
		return PlaceQueryResponses.from(queryPlacesByCategory(stationName, category));
	}

	private List<PlaceQueryResponse> queryAllPlaces(String query) {
		return client.queryAllPlaces(query)
			.stream()
			.map(LocalSearchQueryDto::places)
			.flatMap(List::stream)
			.distinct()
			.map(placeDto -> new PlaceQueryResponse(placeDto.businessName(), placeDto.roadAddress()))
			.toList();
	}

	private List<PlaceQueryResponse> queryPlacesByCategory(String query, PlaceCategory category) {
		return client.queryPlacesByCategory(query, category)
			.places()
			.stream()
			.map(placeDto -> new PlaceQueryResponse(placeDto.businessName(), placeDto.roadAddress()))
			.toList();
	}
}
