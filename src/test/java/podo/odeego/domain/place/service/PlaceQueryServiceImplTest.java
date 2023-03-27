package podo.odeego.domain.place.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import podo.odeego.config.TestConfig;
import podo.odeego.domain.place.dto.PlaceQueryResponse;
import podo.odeego.domain.place.entity.Place;
import podo.odeego.domain.place.entity.PlaceCategory;
import podo.odeego.domain.place.repository.PlaceRepository;
import podo.odeego.domain.station.repository.StationRepository;
import podo.odeego.domain.type.Address;

@ExtendWith(SpringExtension.class)
@Import(TestConfig.class)
@DataJpaTest
class PlaceQueryServiceImplTest {

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private PlaceQueryServiceImpl placeQueryService;

	@Autowired
	private PlaceRepository placeRepository;

	@Test
	@DisplayName("장소를 카테고리별로 조회할 수 있다.")
	void get_all_place() {
		// given
		String gangnamStationName = "강남역";
		placeRepository.saveAll(
			List.of(
				new Place("음식점1", new Address("음식점1 주소"), gangnamStationName, PlaceCategory.RESTAURANT),
				new Place("음식점2", new Address("음식점2 주소"), gangnamStationName, PlaceCategory.RESTAURANT),
				new Place("음식점3", new Address("음식점3 주소"), gangnamStationName, PlaceCategory.RESTAURANT),
				new Place("카페1", new Address("카페1 주소"), gangnamStationName, PlaceCategory.CAFE),
				new Place("카페2", new Address("카페2 주소"), gangnamStationName, PlaceCategory.CAFE),
				new Place("카페3", new Address("카페3 주소"), gangnamStationName, PlaceCategory.CAFE)
			)
		);

		PageRequest pageRequest = PageRequest.of(0, 6);

		// when
		Page<PlaceQueryResponse> allPlaces =
			placeQueryService.getAll(gangnamStationName, PlaceCategory.CAFE, pageRequest);

		// then
		long expectedSize = 3;
		long actualSize = allPlaces.getTotalElements();

		assertThat(actualSize).isEqualTo(expectedSize);
	}
}