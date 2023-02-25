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

	public static PathResponse from(Path path, List<Station> stations) {
		List<StationResponse> stationRespons = convert(stations);
		return new PathResponse(path.start(), path.requiredTime(), stationRespons);
	}

	private static List<StationResponse> convert(List<Station> stations) {
		return stations.stream()
			.map(StationResponse::new)
			.toList();
	}
}
