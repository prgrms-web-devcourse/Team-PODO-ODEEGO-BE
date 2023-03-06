package podo.odeego.domain.midpoint.dto;

import javax.validation.constraints.NotBlank;

import podo.odeego.domain.util.JsonUtils;

public record StartSubmitRequest(

	@NotBlank
	String stationName,

	double lat,

	double lng
) {

	@Override
	public String stationName() {
		return JsonUtils.getStationNameWithoutLine(stationName);
	}
}
