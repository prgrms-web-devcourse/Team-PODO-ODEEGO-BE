package podo.odeego.domain.place.dto;

import podo.odeego.domain.place.entity.Place;
import podo.odeego.domain.place.entity.PlaceCategory;
import podo.odeego.domain.type.Address;

public record PlaceCreateRequest(
	String name,
	String address,
	String stationName,
	PlaceCategory category
) {

	public Place toEntity() {
		return Place.of(name, new Address(address), stationName, category);
	}
}
