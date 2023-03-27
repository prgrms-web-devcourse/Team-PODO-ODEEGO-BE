package podo.odeego.domain.place.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.place.dto.PlaceQueryResponse;
import podo.odeego.domain.place.entity.Place;
import podo.odeego.domain.place.entity.PlaceCategory;
import podo.odeego.domain.place.exception.PlaceNotFoundException;
import podo.odeego.domain.place.repository.PlaceRepository;

@Qualifier("placeQueryServiceImpl")
@Service
@Transactional(readOnly = true)
public class PlaceQueryServiceImpl implements PlaceQueryService {

	private final PlaceRepository placeRepository;

	public PlaceQueryServiceImpl(PlaceRepository placeRepository) {
		this.placeRepository = placeRepository;
	}

	@Override
	public Page<PlaceQueryResponse> getAll(String stationName, Pageable pageable) {
		Page<Place> findPlaces = placeRepository.findPlacesByStationName(stationName, pageable);
		return PlaceQueryResponse.from(findPlaces);
	}

	@Override
	public Page<PlaceQueryResponse> getAll(String stationName, PlaceCategory category, Pageable pageable) {
		Page<Place> findPlaces = placeRepository.findPlacesByStationNameAndCategory(stationName, category, pageable);
		return PlaceQueryResponse.from(findPlaces);
	}

	public Place findById(Long placeId) {
		return placeRepository.findById(placeId)
			.orElseThrow(() -> new PlaceNotFoundException("Cannot find Place for placeId='%d'".formatted(placeId)));
	}
}
