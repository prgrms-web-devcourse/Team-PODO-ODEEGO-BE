package podo.odeego.domain.place.service;

import org.springframework.stereotype.Service;

import podo.odeego.domain.place.dto.PlaceCreateRequest;
import podo.odeego.domain.place.entity.Place;
import podo.odeego.domain.place.repository.PlaceRepository;

@Service
public class PlaceCreateService {

	private final PlaceRepository placeRepository;

	public PlaceCreateService(PlaceRepository placeRepository) {
		this.placeRepository = placeRepository;
	}

	public Long save(PlaceCreateRequest createRequest) {
		Place savedPlace = placeRepository.save(createRequest.toEntity());
		return savedPlace.id();
	}
}
