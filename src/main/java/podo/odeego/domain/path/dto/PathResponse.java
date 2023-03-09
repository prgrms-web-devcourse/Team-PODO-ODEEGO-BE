package podo.odeego.domain.path.dto;

import java.util.List;

import podo.odeego.domain.path.entity.Path;
import podo.odeego.domain.station.dto.StationInfo;
import podo.odeego.domain.station.dto.StationResponse;

public record PathResponse(
	String startStation,
	int time,
	List<StationResponse> stations
) {
	public PathResponse(Path path, List<StationInfo> stations) {
		this(path.startStation(), path.requiredTime(), StationResponse.from(stations));
	}
}
