package podo.odeego.domain.place.service;

import podo.odeego.domain.place.domain.PlaceCategory;
import podo.odeego.domain.place.dto.PlaceResponses;
import podo.odeego.domain.station.dto.StationAddress;

public interface PlaceQueryService {

	PlaceResponses getAll(StationAddress station, PlaceCategory placeCategory);
}