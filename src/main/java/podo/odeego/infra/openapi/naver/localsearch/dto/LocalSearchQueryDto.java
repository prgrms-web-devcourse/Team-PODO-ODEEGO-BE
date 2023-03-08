package podo.odeego.infra.openapi.naver.localsearch.dto;

import java.util.List;

import podo.odeego.domain.place.entity.PlaceCategory;

public record LocalSearchQueryDto(
	LocalSearchRequest request,
	List<PlaceQueryDto> places
) {

	public List<QueryMetaData> toQueryMetaDatas() {
		return QueryMetaData.of(request, places);
	}

	public record QueryMetaData(
		String query,
		PlaceCategory category,
		String businessName,
		String address
	) {

		public static QueryMetaData of(LocalSearchRequest request, PlaceQueryDto placeQueryDto) {
			return new QueryMetaData(
				request.origin(),
				request.category(),
				placeQueryDto.businessName(),
				placeQueryDto.roadAddress()
			);
		}

		public static List<QueryMetaData> of(LocalSearchRequest request, List<PlaceQueryDto> placeQueryDtos) {
			return placeQueryDtos.stream()
				.map(dto -> QueryMetaData.of(request, dto))
				.toList();
		}
	}
}
