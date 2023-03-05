package podo.odeego.infra.openapi.naver.localsearch.dto;

import java.util.List;

public class LocalSearchQueryDto {

	private final LocalSearchRequest request;
	private final List<PlaceQueryDto> places;

	private LocalSearchQueryDto(LocalSearchRequest request, List<PlaceQueryDto> places) {
		this.request = request;
		this.places = places;
	}

	public static LocalSearchQueryDto from(LocalSearchRequest request, LocalSearchResponse response) {
		return new LocalSearchQueryDto(request, response.getPlaces());
	}

	public LocalSearchRequest getRequest() {
		return request;
	}

	public List<PlaceQueryDto> getPlaces() {
		return places;
	}
}
