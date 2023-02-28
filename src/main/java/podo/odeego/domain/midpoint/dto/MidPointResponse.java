package podo.odeego.domain.midpoint.dto;

import java.util.Collections;
import java.util.List;

import podo.odeego.domain.path.dto.PathResponse;
import podo.odeego.domain.station.entity.Station;

public record MidPointResponse(
	String stationName,
	String address,
	Double lat,
	Double lng,
	String line,
	Long id,
	List<PathResponse> path
) {
	public MidPointResponse(Station station, List<PathResponse> pathResponses) {
		this(station.name(), station.address(), station.latitude(), station.longitude(), station.line(), station.id(),
			pathResponses);
	}

	public static MidPointResponse fromSame(Station start) {
		return new MidPointResponse(start, Collections.EMPTY_LIST);
	}
}
