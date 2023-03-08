package podo.odeego.domain.station.dto;

import java.util.List;

import podo.odeego.domain.station.entity.Station;

public record StationNameQueryDto(
	String name
) {

	public static List<StationNameQueryDto> from(List<Station> stations) {
		return stations.stream()
			.map(StationNameQueryDto::from)
			.toList();
	}

	private static StationNameQueryDto from(Station station) {
		return new StationNameQueryDto(station.name());
	}
}
