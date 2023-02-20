package podo.odeego.infra.openapi.naver;

import java.net.URI;

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

	public LocalSearchResponse searchLocal(LocalSearchRequest request) {
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
