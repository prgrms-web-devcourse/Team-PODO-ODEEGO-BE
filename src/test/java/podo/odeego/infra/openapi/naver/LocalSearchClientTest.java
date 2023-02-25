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

import podo.odeego.domain.place.domain.PlaceCategory;
import podo.odeego.domain.place.dto.SimplePlaceResponse;
import podo.odeego.infra.openapi.naver.localsearch.LocalSearchClient;

@SpringBootTest
class LocalSearchClientTest {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LocalSearchClient localSearchClient;

	@Test
	@DisplayName("네이버 지역 검색 API를 호출할 수 있다.")
	void call_naver_local_search_api() {
		// given
		String query = "강남역";

		// when
		List<SimplePlaceResponse> responses = localSearchClient.searchLocal(query);

		// then
		assertThat(responses).isNotNull();
		for (SimplePlaceResponse response : responses) {
			log.info(response.toString());
		}
	}

	@Test
	@DisplayName("장소 카테고리 별로 네이버 지역 검색 API를 호출할 수 있다.")
	void call_naver_local_search_api_by_place_category() {
		// given
		String query = "강남역";
		PlaceCategory cafe = PlaceCategory.CAFE;
		PlaceCategory restaurant = PlaceCategory.RESTAURANT;

		// when
		List<SimplePlaceResponse> searchCafeResponses = localSearchClient.searchLocal(query, cafe);
		List<SimplePlaceResponse> searchRestaurantResponses = localSearchClient.searchLocal(query, restaurant);

		// then
		assertAll(
			() -> assertThat(searchCafeResponses).isNotNull(),
			() -> assertThat(searchRestaurantResponses).isNotNull()
		);
	}
}