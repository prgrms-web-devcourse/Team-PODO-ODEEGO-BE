package podo.odeego.infra.openapi.naver.localsearch.dto;

import java.util.List;

public record LocalSearchQueryDto(
	LocalSearchRequest request,
	List<PlaceQueryDto> places
) {
}
