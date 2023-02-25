package podo.odeego.infra.openapi.naver.imagesearch;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import podo.odeego.infra.openapi.naver.NaverClient;
import podo.odeego.infra.openapi.naver.imagesearch.dto.ImageSearchRequest;
import podo.odeego.infra.openapi.naver.imagesearch.dto.ImageSearchResponse;

@Component
public class ImageSearchClient extends NaverClient {

	public ImageSearchClient(@Value("${naver.url.search.image}") String url) {
		super(url);
	}

	public ImageSearchResponse callImageSearchApi(ImageSearchRequest request) {
		URI uri = super.getUri(request);
		HttpEntity<HttpHeaders> httpEntity = super.getHttpEntity();
		ParameterizedTypeReference<ImageSearchResponse> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<ImageSearchResponse> responseEntity = new RestTemplate()
			.exchange(uri, HttpMethod.GET, httpEntity, responseType);

		return responseEntity.getBody();
	}
}
