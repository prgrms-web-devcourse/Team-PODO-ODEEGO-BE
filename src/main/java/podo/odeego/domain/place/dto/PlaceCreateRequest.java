package podo.odeego.domain.place.dto;

import java.util.List;

import podo.odeego.domain.place.entity.Place;
import podo.odeego.domain.place.entity.PlaceCategory;
import podo.odeego.domain.type.Address;

public record PlaceCreateRequest(
	String name,
	String address,
	String stationName,
	PlaceCategory category
) {

	public static List<Place> toEntity(List<PlaceCreateRequest> requests) {
		return requests.stream()
			.map(PlaceCreateRequest::toEntity)
			.toList();
	}

	public Place toEntity() {
		return Place.of(name, new Address(address), stationName, category);
	}
}
