package podo.odeego.infra.openapi.naver.localsearch.dto;

import java.util.List;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import podo.odeego.domain.place.entity.PlaceCategory;
import podo.odeego.infra.openapi.naver.ClientRequest;

public class LocalSearchRequest extends ClientRequest {

	private static final String SUFFIX_RESTAURANT = " 맛집";
	private static final String SUFFIX_CAFE = " 카페";

	private final int display = 5;
	private final int start = 1;
	private final String sort;

	private final String origin;
	private final PlaceCategory category;

	private LocalSearchRequest(String origin, PlaceCategory category, String query, String sort) {
		super(query);
		this.origin = origin;
		this.category = category;
		this.sort = sort;
	}

	public static LocalSearchRequest of(String query, PlaceCategory category, SortType sortType) {
		return switch (category) {
			case CAFE -> new LocalSearchRequest(query, category, query + SUFFIX_CAFE, sortType.type);
			case RESTAURANT -> new LocalSearchRequest(query, category, query + SUFFIX_RESTAURANT, sortType.type);
			default -> throw new IllegalArgumentException(
				"PlaceCategory '%s' is invalid to generate LocalSearchRequest object.".formatted(category.toString())
			);
		};
	}

	public static List<LocalSearchRequest> newInstancesWithAllSortType(
		String query,
		PlaceCategory category
	) {
		return List.of(
			LocalSearchRequest.of(query, category, LocalSearchRequest.SortType.RANDOM),
			LocalSearchRequest.of(query, category, LocalSearchRequest.SortType.COMMENT)
		);
	}

	@Override
	public MultiValueMap<String, String> toMultiValueMap() {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

		map.add("query", super.query());
		map.add("display", String.valueOf(display));
		map.add("start", String.valueOf(start));
		map.add("sort", sort);

		return map;
	}

	public String origin() {
		return origin;
	}

	public PlaceCategory category() {
		return category;
	}

	public enum SortType {

		RANDOM("random"),
		COMMENT("comment");

		private final String type;

		SortType(String type) {
			this.type = type;
		}
	}
}

