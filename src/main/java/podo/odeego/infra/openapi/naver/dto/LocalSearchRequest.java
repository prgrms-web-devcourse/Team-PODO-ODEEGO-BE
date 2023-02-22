package podo.odeego.infra.openapi.naver.dto;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import podo.odeego.domain.place.domain.PlaceCategory;

public class LocalSearchRequest {

	private static final String SUFFIX_RESTAURANT = " 맛집";
	private static final String SUFFIX_CAFE = " 카페";

	private final String query;
	private final int display = 5;
	private final int start = 1;
	private final String sort;

	private LocalSearchRequest(String query, String sort) {
		this.query = query;
		this.sort = sort;
	}

	public static LocalSearchRequest of(String query, PlaceCategory category, SortType sortType) {
		return switch (category) {
			case CAFE -> new LocalSearchRequest(query + SUFFIX_CAFE, sortType.type);
			case RESTAURANT -> new LocalSearchRequest(query + SUFFIX_RESTAURANT, sortType.type);
		};
	}

	public MultiValueMap<String, String> toMultiValueMap() {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

		map.add("query", query);
		map.add("display", String.valueOf(display));
		map.add("start", String.valueOf(start));
		map.add("sort", sort);

		return map;
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

