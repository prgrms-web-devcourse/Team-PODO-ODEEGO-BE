package podo.odeego.domain.station.dto;

import java.util.List;

import podo.odeego.domain.midpoint.dto.MidPointSearchRequest;

public record StationResponse(
	String stationName,
	Double lat,
	Double lng
) {
	public StationResponse(StationInfo station) {
		this(station.name(), station.latitude(), station.longitude());
	}

	public static StationResponse fromStart(MidPointSearchRequest.Start start) {
		return new StationResponse(start.stationName(), start.lat(), start.lng());
	}

	public static List<StationResponse> from(List<StationInfo> stations) {
		return stations.stream()
			.map(station -> new StationResponse(station.name(), station.latitude(),
				station.longitude()))
			.toList();
	}
}
