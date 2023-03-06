package podo.odeego.domain.midpoint.dto;

import javax.validation.constraints.NotBlank;

public record StartSubmitRequest(

	@NotBlank
	String stationName,

	double lat,

	double lng
) {

	@Override
	public String stationName() {
		if (stationName.endsWith("호선")) {
			return stationName.split(" ")[0];
		}

		return stationName;
	}
}
