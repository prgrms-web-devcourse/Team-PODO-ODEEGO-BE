package podo.odeego.domain.midpoint.dto;

import java.util.List;

import podo.odeego.domain.station.dto.StationResponse;
import podo.odeego.domain.station.entity.Station;

public record MidPointSearchResponse(
	List<StationResponse> start,
	List<MidPointResponse> midPointResponses
) {
	public static MidPointSearchResponse from(List<Station> stations, List<MidPointResponse> midPointResponses) {
		List<StationResponse> start = stations.stream()
			.map(StationResponse::new)
			.toList();
		return new MidPointSearchResponse(start, midPointResponses);
	}

	public static MidPointSearchResponse fromOne(Station start) {
		return new MidPointSearchResponse(List.of(new StationResponse(start)),
			List.of(MidPointResponse.fromSame(start)));
	}
}
