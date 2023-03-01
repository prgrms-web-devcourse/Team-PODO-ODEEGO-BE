package podo.odeego.domain.member.dto;

import podo.odeego.domain.station.entity.Station;

public record MemberDefaultStationGetResponse(
	String stationName,
	double lat,
	double lng
) {
	public MemberDefaultStationGetResponse(Station defaultStation) {
		this(defaultStation.name(), defaultStation.latitude(), defaultStation.longitude());
	}
}
