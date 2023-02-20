package podo.odeego.infra.openapi.naver;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import podo.odeego.infra.openapi.naver.dto.LocalSearchRequest;
import podo.odeego.infra.openapi.naver.dto.LocalSearchResponse;

@SpringBootTest
class NaverClientTest {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private NaverClient naverClient;

	@Test
	@DisplayName("네이버 지역 검색 API를 호출할 수 있다.")
	void call_naver_local_search_api() {
		// given
		LocalSearchRequest cafeRequest =
			LocalSearchRequest.newCafeRequest("강남역", LocalSearchRequest.SortType.RANDOM);
		LocalSearchRequest restaurantRequest =
			LocalSearchRequest.newRestaurantRequest("강남역", LocalSearchRequest.SortType.RANDOM);

		// when
		LocalSearchResponse cafeResponse = naverClient.searchLocal(cafeRequest);
		LocalSearchResponse restaurantResponse = naverClient.searchLocal(restaurantRequest);

		// then
		assertAll(
			() -> assertThat(cafeResponse).isNotNull(),
			() -> assertThat(restaurantResponse).isNotNull()
		);

		log.info(cafeResponse.toString());
		log.info(restaurantResponse.toString());
	}
}