package podo.odeego.domain.place.service;

import podo.odeego.domain.place.dto.PlaceQueryResponses;
import podo.odeego.domain.place.entity.PlaceCategory;

public interface PlaceQueryService {

	PlaceQueryResponses getAll(String stationName, PlaceCategory placeCategory);
}