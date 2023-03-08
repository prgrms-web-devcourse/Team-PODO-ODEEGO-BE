package podo.odeego.domain.place.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import podo.odeego.domain.place.entity.Place;

public class PlaceQueryResponse {

	private String businessName;
	private String address;
	private List<PlaceImageUrl> images;

	public PlaceQueryResponse(String businessName, String address) {
		this(businessName, address, new ArrayList<>());
	}

	public PlaceQueryResponse(String businessName, String address, List<PlaceImageUrl> images) {
		this.businessName = businessName;
		this.address = address;
		this.images = images;
	}

	public static PlaceQueryResponse from(Place place) {
		return new PlaceQueryResponse(place.name(), place.address(), PlaceImageUrl.from(place.images()));
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

	public List<PlaceImageUrl> getImages() {
		return images;
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
