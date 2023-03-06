package podo.odeego.infra.openapi.naver;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import podo.odeego.domain.place.entity.PlaceCategory;
import podo.odeego.infra.openapi.naver.localsearch.client.LocalSearchClient;
import podo.odeego.infra.openapi.naver.localsearch.dto.LocalSearchQueryDto;

@SpringBootTest
class LocalSearchClientTest {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LocalSearchClient client;

	@Test
	@DisplayName("질의어를 사용하여 지역 검색 Open API를 호출할 수 있다.")
	void call_naver_local_search_api() {
		// given
		String query = "강남역";

		// when
		List<LocalSearchQueryDto> queryDtos = client.queryAllPlaces(query);

		// then
		assertThat(queryDtos).isNotNull();
	}

	@Test
	@DisplayName("장소 카테고리 별로 장소를 검색할 수 있다.")
	void search_place_by_place_category() {
		// given
		String query = "강남역";

		PlaceCategory cafe = PlaceCategory.CAFE;
		PlaceCategory restaurant = PlaceCategory.RESTAURANT;

		// when
		LocalSearchQueryDto searchCafeResult = client.queryPlacesByCategory(query, cafe);
		LocalSearchQueryDto searchRestaurantResult = client.queryPlacesByCategory(query, restaurant);

		// then
		assertAll(
			() -> assertThat(searchCafeResult).isNotNull(),
			() -> assertThat(searchRestaurantResult).isNotNull()
		);
	}
}