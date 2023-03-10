package podo.odeego.domain.midpoint.dto;

import java.util.Collections;
import java.util.List;

import podo.odeego.domain.path.dto.PathResponse;
import podo.odeego.domain.station.dto.StationInfo;

public record MidPointResponse(
	String stationName,
	Double lat,
	Double lng,
	String line,
	Long id,
	List<PathResponse> path
) {
	public MidPointResponse(StationInfo station, List<PathResponse> pathResponses) {
		this(station.name(), station.latitude(), station.longitude(), station.line(), station.id(),
			pathResponses);
	}

	public static MidPointResponse fromSame(StationInfo start) {
		return new MidPointResponse(start, Collections.EMPTY_LIST);
	}
}
