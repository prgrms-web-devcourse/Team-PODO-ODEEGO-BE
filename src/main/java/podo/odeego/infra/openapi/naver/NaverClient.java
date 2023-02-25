package podo.odeego.infra.openapi.naver;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

public abstract class NaverClient {

	@Value("${naver.header.id}")
	private String headerId;

	@Value("${naver.header.secret}")
	private String headerSecret;

	@Value("${naver.client.id}")
	private String clientId;

	@Value("${naver.client.secret}")
	private String clientSecret;

	private String url;

	public NaverClient(String url) {
		this.url = url;
	}

	protected URI getUri(ClientRequest request) {
		return UriComponentsBuilder
			.fromUriString(this.url)
			.queryParams(request.toMultiValueMap())
			.build()
			.encode()
			.toUri();
	}

	protected HttpEntity<HttpHeaders> getHttpEntity() {
		return new HttpEntity<>(getHttpHeaders());
	}

	protected HttpHeaders getHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(headerId, clientId);
		headers.set(headerSecret, clientSecret);
		headers.setContentType(MediaType.APPLICATION_JSON);

		return headers;
	}
}
