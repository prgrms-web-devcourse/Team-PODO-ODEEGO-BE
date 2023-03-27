package podo.odeego.domain.place.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import podo.odeego.domain.place.dto.PlaceQueryResponse;
import podo.odeego.domain.place.entity.PlaceCategory;

public interface PlaceQueryService {

	Page<PlaceQueryResponse> getAll(String stationName, Pageable pageable);

	Page<PlaceQueryResponse> getAll(String stationName, PlaceCategory category, Pageable pageable);
}