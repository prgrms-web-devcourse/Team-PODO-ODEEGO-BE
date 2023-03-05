package podo.odeego.infra.openapi.naver.localsearch.dto;

import java.util.List;

public class NaverPlaceQueryResponses {

	private final List<NaverPlaceQueryResponse> naverPlaceQueryRespons;

	private NaverPlaceQueryResponses(List<NaverPlaceQueryResponse> naverPlaceQueryRespons) {
		this.naverPlaceQueryRespons = naverPlaceQueryRespons;
	}

	public static NaverPlaceQueryResponses from(List<NaverPlaceQueryResponse> naverPlaceQueryRespons) {
		return new NaverPlaceQueryResponses(naverPlaceQueryRespons);
	}

	public static NaverPlaceQueryResponses from(LocalSearchQueryDto response) {
		return response.
	}

	public List<NaverPlaceQueryResponse> getPlaceQueryResponses() {
		return naverPlaceQueryRespons;
	}
}
