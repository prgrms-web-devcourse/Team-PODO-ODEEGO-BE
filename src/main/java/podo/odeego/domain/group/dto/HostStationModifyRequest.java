package podo.odeego.domain.group.dto;

import javax.validation.constraints.NotBlank;

import podo.odeego.domain.util.JsonUtils;

public record HostStationModifyRequest(

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
