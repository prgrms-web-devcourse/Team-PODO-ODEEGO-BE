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
	public PathResponse(Path path, List<Station> stations) {
		this(path.start(), path.requiredTime(), StationResponse.from(stations));
	}
}
