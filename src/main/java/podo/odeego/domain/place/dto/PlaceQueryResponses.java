package podo.odeego.domain.place.dto;

import java.util.List;

public class PlaceQueryResponses {

	private final List<PlaceQueryResponse> places;

	private PlaceQueryResponses(List<PlaceQueryResponse> places) {
		this.places = places;
	}

	public static PlaceQueryResponses from(List<PlaceQueryResponse> places) {
		return new PlaceQueryResponses(places);
	}

	public List<PlaceQueryResponse> getPlaces() {
		return places;
	}
}