package podo.odeego.domain.place.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.place.dto.PlaceCreateRequest;
import podo.odeego.domain.place.entity.Place;
import podo.odeego.domain.place.repository.PlaceRepository;

@Service
@Transactional
public class PlaceCreateService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final PlaceRepository placeRepository;

	public PlaceCreateService(PlaceRepository placeRepository) {
		this.placeRepository = placeRepository;
	}

	public Long save(PlaceCreateRequest createRequest) {
		Place savedPlace = placeRepository.save(createRequest.toEntity());
		return savedPlace.id();
	}

	public void saveAll(List<PlaceCreateRequest> placeCreateRequests) {
		List<Place> places = placeRepository.saveAll(PlaceCreateRequest.toEntity(placeCreateRequests));
		log.info("'%d' places has been saved.".formatted(places.size()));
	}
}
