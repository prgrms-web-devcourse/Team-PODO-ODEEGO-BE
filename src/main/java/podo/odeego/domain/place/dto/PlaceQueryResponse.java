package podo.odeego.domain.place.dto;

import java.util.List;
import java.util.Objects;

import podo.odeego.domain.place.entity.Place;

public class PlaceQueryResponse {

	private String businessName;
	private String address;

	public PlaceQueryResponse(String businessName, String address) {
		this.businessName = businessName;
		this.address = address;
	}

	public static PlaceQueryResponse from(Place place) {
		return new PlaceQueryResponse(place.name(), place.address());
	}

	public static List<PlaceQueryResponse> from(List<Place> places) {
		return places.stream()
			.map(PlaceQueryResponse::from)
			.distinct()
			.toList();
	}

	public String getBusinessName() {
		return businessName;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PlaceQueryResponse that = (PlaceQueryResponse)o;
		return Objects.equals(businessName, that.businessName) && Objects.equals(address, that.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(businessName, address);
	}
}
