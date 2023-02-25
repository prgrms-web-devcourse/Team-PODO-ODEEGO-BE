package podo.odeego.infra.openapi.naver.localsearch.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import podo.odeego.domain.place.domain.PlaceCategory;
import podo.odeego.domain.place.dto.PlaceResponses;
import podo.odeego.domain.place.service.PlaceQueryService;
import podo.odeego.domain.station.dto.StationAddress;
import podo.odeego.infra.openapi.naver.localsearch.LocalSearchClient;

@Service
@Qualifier("naverPlaceQueryService")
public class NaverPlaceQueryService implements PlaceQueryService {

	private final LocalSearchClient localSearchClient;

	public NaverPlaceQueryService(LocalSearchClient localSearchClient) {
		this.localSearchClient = localSearchClient;
	}

	@Override
	public PlaceResponses getAll(StationAddress station, PlaceCategory category) {
		if (category == null) {
			return new PlaceResponses(localSearchClient.searchLocal(station.name()));
		}
		return new PlaceResponses(localSearchClient.searchLocal(station.name(), category));
	}
}
