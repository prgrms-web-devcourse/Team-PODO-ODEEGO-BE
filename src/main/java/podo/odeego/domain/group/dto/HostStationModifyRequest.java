package podo.odeego.domain.group.dto;

import javax.validation.constraints.NotBlank;

public record HostStationModifyRequest(

	@NotBlank
	String stationName,

	double lat,

	double lng
) {
}
