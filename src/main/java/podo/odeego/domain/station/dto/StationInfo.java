package podo.odeego.domain.station.dto;

import podo.odeego.domain.station.entity.Station;

public record StationInfo(
	Long id,
	String name,
	double latitude,
	double longitude,
	String line
) {

	public StationInfo(Station station) {
		this(station.id(), station.name(), station.latitude(), station.longitude(), station.line());
	}

	public Station toEntity() {
		return new Station(
			id,
			name,
			latitude,
			longitude,
			line
		);
	}
}
