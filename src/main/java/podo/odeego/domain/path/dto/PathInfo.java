package podo.odeego.domain.path.dto;

import java.util.List;

import podo.odeego.domain.path.entity.Path;

public record PathInfo(
	String startStation,
	String endStation,
	int requiredTime,
	String stations
) {

	public PathInfo(Path path) {
		this(path.startStation(), path.endStation(), path.requiredTime(), path.stations());
	}

	public List<String> getStations() {
		return List.of(stations.split("-"));
	}
}
