package podo.odeego.domain.path.dto;

import java.util.List;

import podo.odeego.domain.path.entity.Path;
import podo.odeego.domain.station.dto.StationResponse;
import podo.odeego.domain.station.entity.Station;

public record PathResponse(
	String startStation,
	int time,
	List<StationResponse> stations
) {

	public static PathResponse from(Path path, List<Station> routeStations) {
		List<StationResponse> stationResponse = convert(routeStations);
		return new PathResponse(path.start(), path.requiredTime(), stationResponse);
	}

	private static List<StationResponse> convert(List<Station> stations) {
		return stations.stream()
			.map(StationResponse::new)
			.toList();
	}
}
