package podo.odeego.domain.member.dto;

import podo.odeego.domain.station.dto.StationInfo;

public record MemberDefaultStationGetResponse(
	String stationName,
	double lat,
	double lng
) {
	public MemberDefaultStationGetResponse(StationInfo defaultStation) {
		this(defaultStation.name(), defaultStation.latitude(), defaultStation.longitude());
	}
}
