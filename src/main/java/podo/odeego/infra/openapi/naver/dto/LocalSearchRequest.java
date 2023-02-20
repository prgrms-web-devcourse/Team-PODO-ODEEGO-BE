package podo.odeego.infra.openapi.naver.dto;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class LocalSearchRequest {

	private static final String QUERY_SUFFIX_RESTAURANT = " 맛집";
	private static final String QUERY_SUFFIX_CAFE = " 카페";

	private final String query;
	private final int display = 5;
	private final int start = 1;
	private final String sort;

	private LocalSearchRequest(String query, String sort) {
		this.query = query;
		this.sort = sort;
	}

	public static LocalSearchRequest newRestaurantRequest(String query, SortType sortType) {
		return new LocalSearchRequest(query + QUERY_SUFFIX_RESTAURANT, sortType.literal);
	}

	public static LocalSearchRequest newCafeRequest(String query, SortType sortType) {
		return new LocalSearchRequest(query + QUERY_SUFFIX_CAFE, sortType.literal);
	}

	public MultiValueMap<String, String> toMultiValueMap() {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

		map.add("query", query);
		map.add("display", String.valueOf(display));
		map.add("display", String.valueOf(start));
		map.add("display", sort);

		return map;
	}

	public enum SortType {

		RANDOM("random"),
		COMMENT("comment");

		private final String literal;

		SortType(String literal) {
			this.literal = literal;
		}
	}
}

