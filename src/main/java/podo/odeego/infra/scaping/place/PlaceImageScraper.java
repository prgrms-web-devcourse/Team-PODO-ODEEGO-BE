package podo.odeego.infra.scaping.place;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import podo.odeego.domain.place.dto.PlaceImageAddRequest;
import podo.odeego.domain.place.entity.Place;
import podo.odeego.domain.place.repository.PlaceRepository;
import podo.odeego.domain.place.service.PlaceImageAddService;
import podo.odeego.infra.openapi.naver.imagesearch.client.ImageSearchClient;
import podo.odeego.infra.openapi.naver.imagesearch.dto.ImageQueryDto;
import podo.odeego.infra.scaping.place.util.TimeSleeper;

@Component
public class PlaceImageScraper {

	private static final String BLANK_SPACE = " ";

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final ImageSearchClient imageSearchClient;
	private final PlaceRepository placeRepository;
	private final PlaceImageAddService placeImageAddService;

	public PlaceImageScraper(
		ImageSearchClient imageSearchClient,
		PlaceRepository placeRepository,
		PlaceImageAddService placeImageAddService
	) {
		this.imageSearchClient = imageSearchClient;
		this.placeRepository = placeRepository;
		this.placeImageAddService = placeImageAddService;
	}

	private static String generateImageQuery(Place place) {
		return place.name() + BLANK_SPACE + place.stationName();
	}

	public void scrapImagesByPlace() {
		List<Place> allPlaces = placeRepository.findAll();
		log.info("Start scrap images for places. placeCount=%d".formatted(allPlaces.size()));

		allPlaces.forEach(place -> {
			String query = generateImageQuery(place);
			log.info("Scrap images for query=%s".formatted(query));
			List<ImageQueryDto> queryResult = imageSearchClient.queryImages(query);
			saveImages(place, queryResult);
			TimeSleeper.waitForWhile(200);
		});
	}

	private void saveImages(Place place, List<ImageQueryDto> imageQueryDtos) {
		placeImageAddService.addImages(
			new PlaceImageAddRequest(place.id(), ImageQueryDto.toPlaceImageCreateRequests(imageQueryDtos))
		);
	}
}
