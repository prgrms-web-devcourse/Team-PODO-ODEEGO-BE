package podo.odeego.domain.place.dto;

import java.util.Objects;

public record SimplePlaceResponse(
	String businessName,
	String address
) {

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		SimplePlaceResponse that = (SimplePlaceResponse)o;
		return Objects.equals(businessName, that.businessName) && Objects.equals(address, that.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(businessName, address);
	}
}
