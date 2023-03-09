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
	public Page<PlaceQueryResponse> getAll(String stationName, PlaceCategory placeCategory, Pageable pageable) {
		if (placeCategory.isAll()) {
			return getAllByStationName(stationName, pageable);
		}
		return getAllByStationNameAndCategory(stationName, placeCategory, pageable);
	}

	private Page<PlaceQueryResponse> getAllByStationName(String stationName, Pageable pageable) {
		return PlaceQueryResponse.from(placeRepository.findPlacesByStationName(stationName, pageable));
	}

	private Page<PlaceQueryResponse> getAllByStationNameAndCategory(
		String stationName,
		PlaceCategory category,
		Pageable pageable
	) {
		return PlaceQueryResponse.from(
			placeRepository.findPlacesByStationNameAndCategory(stationName, category, pageable)
		);
	}

	public Place findById(Long placeId) {
		return placeRepository.findById(placeId)
			.orElseThrow(() -> new PlaceNotFoundException("Cannot find Place for placeId='%d'".formatted(placeId)));
	}
}
