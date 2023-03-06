package podo.odeego.domain.place.dto;

import java.util.Objects;

public class PlaceQueryResponse {

	private String businessName;
	private String address;

	public PlaceQueryResponse(String businessName, String address) {
		this.businessName = businessName;
		this.address = address;
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
