package podo.odeego.domain.place.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.place.dto.PlaceImageAddRequest;
import podo.odeego.domain.place.dto.PlaceImageCreateRequest;
import podo.odeego.domain.place.entity.Place;
import podo.odeego.domain.place.entity.PlaceImage;
import podo.odeego.domain.place.exception.PlaceImageDuplicatedException;
import podo.odeego.domain.type.Image;

@Service
@Transactional
public class PlaceImageAddService {

	public static final long MAX_IMAGE_COUNT = 5L;
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final PlaceQueryServiceImpl placeQueryService;

	public PlaceImageAddService(PlaceQueryServiceImpl placeQueryService) {
		this.placeQueryService = placeQueryService;
	}

	public void addImages(PlaceImageAddRequest request) {
		Place place = placeQueryService.findById(request.placeId());
		request.imageCreateRequests()
			.stream()
			.limit(MAX_IMAGE_COUNT)
			.forEach(createRequest -> addImage(place, createRequest));
	}

	private void addImage(Place place, PlaceImageCreateRequest createRequest) {
		try {
			place.addImage(new PlaceImage(createRequest.source(), new Image(createRequest.url())));
		} catch (PlaceImageDuplicatedException e) {
			log.info("PlaceImageDuplicatedException occurred for image url=%s".formatted(createRequest.url()));
		}
	}
}
