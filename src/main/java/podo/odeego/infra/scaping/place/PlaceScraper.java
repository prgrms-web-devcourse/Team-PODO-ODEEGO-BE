package podo.odeego.infra.scaping.place;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import podo.odeego.domain.place.dto.PlaceCreateRequest;
import podo.odeego.domain.place.service.PlaceCreateService;
import podo.odeego.domain.station.dto.StationNameQueryDto;
import podo.odeego.domain.station.service.StationFindService;
import podo.odeego.infra.openapi.naver.localsearch.client.LocalSearchClient;
import podo.odeego.infra.openapi.naver.localsearch.dto.LocalSearchQueryDto;
import podo.odeego.infra.scaping.place.util.TimeSleeper;

@Component
public class PlaceScraper {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final LocalSearchClient localSearchClient;

	private final StationFindService stationFindService;
	private final PlaceCreateService placeCreateService;

	public PlaceScraper(
		LocalSearchClient localSearchClient,
		StationFindService stationFindService,
		PlaceCreateService placeCreateService
	) {
		this.localSearchClient = localSearchClient;
		this.stationFindService = stationFindService;
		this.placeCreateService = placeCreateService;
	}

	public void scrapRestaurantsAndCafesNearByStation() {
		List<StationNameQueryDto> stationNames = stationFindService.getAllStationName();

		stationNames.forEach(stationName -> {
			List<PlaceCreateRequest> createRequests = queryLocalPlaces(stationName.name());
			placeCreateService.saveAll(createRequests);
			TimeSleeper.waitForWhile(TimeSleeper.DEFAULT_SLEEP_TIME);
		});
	}

	private List<PlaceCreateRequest> queryLocalPlaces(String query) {
		return localSearchClient.queryAllPlaces(query)
			.stream()
			.map(LocalSearchQueryDto::toQueryMetaDatas)
			.flatMap(List::stream)
			.map(metaData -> new PlaceCreateRequest(
				metaData.businessName(),
				metaData.address(),
				metaData.query(),
				metaData.category())
			)
			.toList();
	}
}
