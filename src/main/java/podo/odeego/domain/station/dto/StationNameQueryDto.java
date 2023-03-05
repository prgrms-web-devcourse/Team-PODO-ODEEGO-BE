package podo.odeego.domain.station.dto;

import java.util.List;

import podo.odeego.domain.station.entity.Station;

public class StationNameQueryDto {

	private final String name;

	private StationNameQueryDto(String name) {
		this.name = name;
	}

	public static List<StationNameQueryDto> from(List<Station> stations) {
		return stations.stream()
			.map(StationNameQueryDto::from)
			.toList();
	}

	private static StationNameQueryDto from(Station station) {
		return new StationNameQueryDto(station.name());
	}

	public String name() {
		return name;
	}
}
