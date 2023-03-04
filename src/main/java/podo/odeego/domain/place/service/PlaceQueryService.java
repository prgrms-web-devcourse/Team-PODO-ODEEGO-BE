package podo.odeego.domain.place.service;

import podo.odeego.domain.place.dto.PlaceResponses;
import podo.odeego.domain.place.entity.PlaceCategory;

public interface PlaceQueryService {

	PlaceResponses getAll(String stationName, PlaceCategory placeCategory);
}