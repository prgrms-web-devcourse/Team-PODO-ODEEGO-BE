package podo.odeego.infra.openapi.naver.imagesearch.dto;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import podo.odeego.infra.openapi.naver.ClientRequest;

public class ImageSearchRequest extends ClientRequest {

	private final int display = 10;
	private final int start = 1;
	private final String sort = "sim"; // sim, date
	private final String filter = "all"; // all, large, medium, small

	public ImageSearchRequest(String query) {
		super(query);
	}

	public MultiValueMap<String, String> toMultiValueMap() {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

		map.add("query", super.query());
		map.add("display", String.valueOf(display));
		map.add("start", String.valueOf(start));
		map.add("sort", sort);
		map.add("filter", filter);

		return map;
	}
}
