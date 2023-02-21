package podo.odeego.infra.openapi.naver;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import podo.odeego.domain.place.domain.PlaceCategory;
import podo.odeego.domain.place.dto.SimplePlaceResponse;
import podo.odeego.infra.openapi.naver.dto.LocalSearchRequest;
import podo.odeego.infra.openapi.naver.dto.LocalSearchResponse;

@Component
public class NaverClient {

	@Value("${naver.header.id}")
	private String headerId;

	@Value("${naver.header.secret}")
	private String headerSecret;

	@Value("${naver.client.id}")
	private String clientId;

	@Value("${naver.client.secret}")
	private String clientSecret;

	@Value("${naver.url.search.local}")
	private String localSearchUrl;

	public List<SimplePlaceResponse> searchLocal(String query) {
		return Arrays.stream(PlaceCategory.values())
			.map(category -> searchLocal(query, category))
			.flatMap(List::stream)
			.distinct()
			.toList();
	}

	public List<SimplePlaceResponse> searchLocal(String query, PlaceCategory category) {
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
		URI uri = getUri(request);
		HttpEntity<HttpHeaders> httpEntity = getHttpEntity();
		ParameterizedTypeReference<LocalSearchResponse> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<LocalSearchResponse> responseEntity = new RestTemplate()
			.exchange(uri, HttpMethod.GET, httpEntity, responseType);

		return responseEntity.getBody();
	}

	private URI getUri(LocalSearchRequest request) {
		return UriComponentsBuilder
			.fromUriString(this.localSearchUrl)
			.queryParams(request.toMultiValueMap())
			.build()
			.encode()
			.toUri();
	}

	private HttpEntity<HttpHeaders> getHttpEntity() {
		return new HttpEntity<>(getHttpHeaders());
	}

	private HttpHeaders getHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(headerId, clientId);
		headers.set(headerSecret, clientSecret);
		headers.setContentType(MediaType.APPLICATION_JSON);

		return headers;
	}
}
