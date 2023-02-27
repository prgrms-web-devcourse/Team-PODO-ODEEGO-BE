package podo.odeego.infra.openapi.naver;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import podo.odeego.domain.place.domain.PlaceCategory;
import podo.odeego.domain.place.dto.PlaceResponses;
import podo.odeego.domain.place.service.PlaceQueryService;
import podo.odeego.domain.station.dto.StationInfo;

@Service
@Qualifier("naverPlaceQueryService")
public class NaverPlaceQueryService implements PlaceQueryService {

	private final NaverClient naverClient;

	public NaverPlaceQueryService(NaverClient naverClient) {
		this.naverClient = naverClient;
	}

	@Override
	public PlaceResponses getAll(StationInfo station, PlaceCategory category) {
		if (category == null) {
			return new PlaceResponses(naverClient.searchLocal(station.name()));
		}
		return new PlaceResponses(naverClient.searchLocal(station.name(), category));
	}
}
