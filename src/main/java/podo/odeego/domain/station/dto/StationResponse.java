package podo.odeego.domain.station.dto;

import java.util.List;

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

	public static List<StationResponse> from(List<Station> stations) {
		return stations.stream()
			.map(station -> new StationResponse(station.name(), station.address(), station.latitude(),
				station.longitude()))
			.toList();
	}
}
