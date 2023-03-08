package podo.odeego.domain.place.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.place.dto.PlaceQueryResponse;
import podo.odeego.domain.place.dto.PlaceQueryResponses;
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
	public PlaceQueryResponses getAll(String stationName, PlaceCategory placeCategory) {
		if (placeCategory.isAll()) {
			return getAllByStationName(stationName);
		}
		return getAllByStationNameAndCategory(stationName, placeCategory);
	}

	private PlaceQueryResponses getAllByStationName(String stationName) {
		List<Place> findPlaces = placeRepository.findPlacesByStationName(stationName);
		return PlaceQueryResponses.from(PlaceQueryResponse.from(findPlaces));
	}

	private PlaceQueryResponses getAllByStationNameAndCategory(String stationName, PlaceCategory category) {
		List<Place> findPlaces = placeRepository.findPlacesByStationNameAndCategory(stationName, category);
		return PlaceQueryResponses.from(PlaceQueryResponse.from(findPlaces));
	}

	public Place findById(Long placeId) {
		return placeRepository.findById(placeId)
			.orElseThrow(() -> new PlaceNotFoundException("Cannot find Place for placeId='%d'".formatted(placeId)));
	}
}
