package podo.odeego.infra.scaping.place;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import podo.odeego.domain.place.entity.Place;
import podo.odeego.domain.place.entity.PlaceCategory;
import podo.odeego.domain.place.entity.PlaceImage;
import podo.odeego.domain.place.repository.PlaceImageRepository;
import podo.odeego.domain.place.repository.PlaceRepository;
import podo.odeego.domain.type.Address;

@SpringBootTest
class PlaceImageScraperTest {

	@Autowired
	private PlaceImageScraper placeImageScraper;

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private PlaceImageRepository placeImageRepository;

	@Test
	@DisplayName("한 장소에 대해 한 개 이상의 이미지를 스크래핑할 수 있다.")
	void scrap_image_place_using_open_api() {
		// given
		Place savedPlace = placeRepository.save(
			Place.of("어글리스토브 강남역", new Address("givenAddress"), "강남역", PlaceCategory.CAFE));

		// when
		placeImageScraper.scrapImagesByPlace();

		// then
		List<PlaceImage> actualSavedImages = placeImageRepository.findAll();
		int minExpectedSize = 1;

		assertThat(actualSavedImages).hasSizeGreaterThanOrEqualTo(minExpectedSize);
	}
}