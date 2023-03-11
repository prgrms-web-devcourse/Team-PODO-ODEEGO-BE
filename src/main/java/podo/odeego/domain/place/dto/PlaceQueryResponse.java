package podo.odeego.domain.place.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;

import podo.odeego.domain.place.entity.Place;

public class PlaceQueryResponse {

	private static final String EMPTY_STRING = "";

	private String businessName;
	private String address;
	private String shareUrl;
	private List<PlaceImageUrl> images;

	public PlaceQueryResponse(String businessName, String address) {
		this(businessName, address, EMPTY_STRING, new ArrayList<>());
	}

	public PlaceQueryResponse(String businessName, String address, String shareUrl, List<PlaceImageUrl> images) {
		this.businessName = businessName;
		this.address = address;
		this.shareUrl = shareUrl;
		this.images = images;
	}

	public static PlaceQueryResponse from(Place place) {
		return new PlaceQueryResponse(
			place.name(),
			place.address(),
			place.shareUrl(),
			PlaceImageUrl.from(place.images())
		);
	}

	public static Page<PlaceQueryResponse> from(Page<Place> places) {
		return places.map(PlaceQueryResponse::from);
	}

	public String getBusinessName() {
		return businessName;
	}

	public String getAddress() {
		return address;
	}

	public String getShareUrl() {
		return shareUrl;
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
