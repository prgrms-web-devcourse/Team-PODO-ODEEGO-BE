package podo.odeego.infra.openapi.kakao;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import podo.odeego.infra.openapi.kakao.dto.GetUserInfoResponse;
import podo.odeego.infra.openapi.kakao.dto.KakaoProfileResponse;
import podo.odeego.infra.openapi.kakao.exception.KakaoClientErrorException;

@Component
public class KakaoClient {

	private static final String AUTHORIZATION = "Authorization";

	private final String userInfoUri;

	public KakaoClient(
		@Value("${oauth2.client.provider.user-info-uri}") String userInfoUri
	) {
		this.userInfoUri = userInfoUri;
	}

	public KakaoProfileResponse getUserInfo(String oAuth2Token) {
		HttpEntity<Void> apiRequest = generateRequest(oAuth2Token);
		ResponseEntity<GetUserInfoResponse> response = sendUserInfoRequest(apiRequest);

		return KakaoProfileResponse.from(
			Objects.requireNonNull(response.getBody())
		);
	}

	private HttpEntity<Void> generateRequest(String oAuth2Token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add(AUTHORIZATION, oAuth2Token);
		return new HttpEntity<>(headers);
	}

	private ResponseEntity<GetUserInfoResponse> sendUserInfoRequest(
		HttpEntity<Void> request
	) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		try {
			return restTemplate.exchange(userInfoUri, HttpMethod.GET, request, GetUserInfoResponse.class);
		} catch (HttpClientErrorException e) {
			throw new KakaoClientErrorException(e.getMessage());
		}
	}
}
