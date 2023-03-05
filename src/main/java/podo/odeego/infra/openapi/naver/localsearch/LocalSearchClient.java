package podo.odeego.infra.openapi.naver.localsearch;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import podo.odeego.domain.place.dto.PlaceSimpleResponse;
import podo.odeego.domain.place.entity.PlaceCategory;
import podo.odeego.infra.openapi.naver.NaverClient;
import podo.odeego.infra.openapi.naver.localsearch.dto.LocalSearchRequest;
import podo.odeego.infra.openapi.naver.localsearch.dto.LocalSearchResponse;

@Component
public class LocalSearchClient extends NaverClient {

	public LocalSearchClient(@Value("${naver.url.search.local}") String url) {
		super(url);
	}

	public List<PlaceSimpleResponse> searchLocal(String query) {
		return Arrays.stream(PlaceCategory.values())
			.map(category -> searchLocal(query, category))
			.flatMap(List::stream)
			.distinct()
			.toList();
	}

	public List<PlaceSimpleResponse> searchLocal(String query, PlaceCategory category) {
		return getLocalSearchRequests(query, category).stream()
			.map(this::callLocalSearchApi)
			.map(LocalSearchResponse::getPlaces)
			.flatMap(List::stream)
			.distinct()
			.toList();
	}

	private List<LocalSearchRequest> getLocalSearchRequests(String query, PlaceCategory category) {
		return List.of(
			LocalSearchRequest.of(query, category, LocalSearchRequest.SortType.RANDOM),
			LocalSearchRequest.of(query, category, LocalSearchRequest.SortType.COMMENT)
		);
	}

	private LocalSearchResponse callLocalSearchApi(LocalSearchRequest request) {
		URI uri = super.getUri(request);
		HttpEntity<HttpHeaders> httpEntity = getHttpEntity();
		ParameterizedTypeReference<LocalSearchResponse> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<LocalSearchResponse> responseEntity = new RestTemplate()
			.exchange(uri, HttpMethod.GET, httpEntity, responseType);

		return responseEntity.getBody();
	}
}
