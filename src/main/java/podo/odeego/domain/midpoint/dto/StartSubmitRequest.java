package podo.odeego.domain.midpoint.dto;

import javax.validation.constraints.NotBlank;

public record StartSubmitRequest(

	@NotBlank
	String stationName,

	double lat,

	double lng
) {
}
