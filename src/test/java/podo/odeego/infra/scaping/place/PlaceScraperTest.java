package podo.odeego.infra.scaping.place;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import podo.odeego.domain.station.repository.StationRepository;

@SpringBootTest
class PlaceScraperTest {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PlaceScraper placeScraper;

	@Autowired
	private StationRepository stationRepository;

	@Test
	@DisplayName("something crazy")
	void testMethodNameHere() {
		// // given
		// stationRepository.saveAll(List.of(
		// 	new Station("강남역", null, 0.0, 0.0, "1호"),
		// 	new Station("이수역", null, 0.0, 0.0, "2호")
		// ));
		//
		// List<PlaceCreateRequest> placeCreateRequests = placeScraper.scrapRestaurantsAndCafesNearByStation();
		// log.info(String.valueOf(placeCreateRequests.size()));
		// for (PlaceCreateRequest placeCreateRequest : placeCreateRequests) {
		// 	log.info(placeCreateRequest.toString());
		// }
		//
		//
		// // when

		// then
	}
}