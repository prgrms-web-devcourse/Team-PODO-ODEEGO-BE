package podo.odeego.domain.station.dto;

import podo.odeego.domain.station.entity.Station;

public record StationResponse(
	String name,
	String address,
	Double lat,
	Double lng
) {
	public StationResponse(Station station) {
		this(station.name(), station.address(), station.latitude(), station.longitude());
	}
}
