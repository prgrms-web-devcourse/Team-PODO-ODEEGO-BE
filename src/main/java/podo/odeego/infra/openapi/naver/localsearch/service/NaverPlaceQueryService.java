package podo.odeego.infra.openapi.naver.localsearch.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import podo.odeego.domain.place.dto.PlaceResponses;
import podo.odeego.domain.place.entity.PlaceCategory;
import podo.odeego.domain.place.service.PlaceQueryService;
import podo.odeego.domain.station.service.StationFindService;
import podo.odeego.infra.openapi.naver.localsearch.LocalSearchClient;

@Service
@Qualifier("naverPlaceQueryService")
public class NaverPlaceQueryService implements PlaceQueryService {

	private final LocalSearchClient localSearchClient;

	private final StationFindService stationFindService;

	public NaverPlaceQueryService(
		LocalSearchClient localSearchClient,
		StationFindService stationFindService
	) {
		this.localSearchClient = localSearchClient;
		this.stationFindService = stationFindService;
	}

	@Override
	public PlaceResponses getAll(String stationName, PlaceCategory category) {
		stationFindService.verifyStationExists(stationName);

		if (category == null) {
			return new PlaceResponses(localSearchClient.searchLocal(stationName));
		}
		return new PlaceResponses(localSearchClient.searchLocal(stationName, category));
	}
}
