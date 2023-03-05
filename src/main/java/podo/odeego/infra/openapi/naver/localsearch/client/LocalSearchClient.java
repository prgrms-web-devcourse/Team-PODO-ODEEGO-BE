package podo.odeego.infra.openapi.naver.localsearch.client;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import podo.odeego.domain.place.entity.PlaceCategory;
import podo.odeego.infra.openapi.naver.NaverClient;
import podo.odeego.infra.openapi.naver.localsearch.dto.LocalSearchQueryDto;
import podo.odeego.infra.openapi.naver.localsearch.dto.LocalSearchRequest;
import podo.odeego.infra.openapi.naver.localsearch.dto.LocalSearchResponse;
import podo.odeego.infra.openapi.naver.localsearch.dto.PlaceQueryDto;

@Component
public class LocalSearchClient extends NaverClient {

	public static final int DEFAULT_REQUEST_INDEX = 0;

	public LocalSearchClient(@Value("${naver.url.search.local}") String url) {
		super(url);
	}

	public List<LocalSearchQueryDto> queryAllPlaces(String query) {
		return PlaceCategory.getSpecifiedValues()
			.stream()
			.map(placeCategory -> queryPlacesByCategory(query, placeCategory))
			.toList();
	}

	public LocalSearchQueryDto queryPlacesByCategory(String query, PlaceCategory category) {
		List<LocalSearchRequest> requests = LocalSearchRequest.newInstancesWithAllSortType(query, category);
		return new LocalSearchQueryDto(requests.get(DEFAULT_REQUEST_INDEX), getDistinctPlaces(requests));
	}

	private List<PlaceQueryDto> getDistinctPlaces(List<LocalSearchRequest> requests) {
		return requests.stream()
			.map(this::executeApiCall)
			.map(PlaceQueryDto::from)
			.flatMap(List::stream)
			.distinct()
			.toList();
	}

	private LocalSearchResponse executeApiCall(LocalSearchRequest request) {
		URI uri = super.getUri(request);
		HttpEntity<HttpHeaders> httpEntity = getHttpEntity();
		ParameterizedTypeReference<LocalSearchResponse> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<LocalSearchResponse> responseEntity = new RestTemplate()
			.exchange(uri, HttpMethod.GET, httpEntity, responseType);

		return responseEntity.getBody();
	}
}
