package podo.odeego.domain.place.service;

import org.springframework.beans.factory.annotation.Qualifier;

import podo.odeego.domain.place.dto.PlaceQueryResponses;
import podo.odeego.domain.place.entity.PlaceCategory;

@Qualifier("placeQueryService")
public class PlaceQueryServiceImpl implements PlaceQueryService {

	@Override
	public PlaceQueryResponses getAll(String stationName, PlaceCategory placeCategory) {
		return null;
	}
}
