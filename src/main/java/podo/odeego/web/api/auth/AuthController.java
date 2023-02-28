package podo.odeego.web.api.auth;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import podo.odeego.domain.member.dto.MemberJoinResponse;
import podo.odeego.domain.member.service.MemberService;
import podo.odeego.web.api.auth.dto.OAuth2GetTokenResponse;
import podo.odeego.web.security.jwt.JwtProvider;
import podo.odeego.web.security.oauth2.OAuth2LoginResponse;

@RestController("api/v1/auth")
public class AuthController {

	private final MemberService memberService;
	private final JwtProvider jwtProvider;

	public AuthController(MemberService memberService, JwtProvider jwtProvider) {
		this.memberService = memberService;
		this.jwtProvider = jwtProvider;
	}

	@GetMapping("/login/oauth2/code/kakao")
	public String loginWithKakao(@RequestParam String code) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Accept", "application/json");
		System.out.println("code = " + code);

		LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", "authorization_code");
		map.add("client_id", "ed27e4021a91d2ffac9494210229ed63");
		map.add("redirect_uri", "http://localhost:8080/login/oauth2/code/kakao");
		map.add("code", code);
		// map.add("client_secret", "vAsIildbPPJAj0PvQs8hjpWcXdzEreib");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		ResponseEntity<OAuth2GetTokenResponse> response = restTemplate.postForEntity(
			"https://kauth.kakao.com/oauth/token",
			request, OAuth2GetTokenResponse.class);
		// String accessToken = response.getBody().access_token();
		// getMemberInfo(accessToken);

		return response.getBody().toString();
	}

	private OAuth2LoginResponse getMemberInfo(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Authorization", "Bearer " + accessToken);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		ResponseEntity<String> response = restTemplate.exchange(
			"https://kapi.kakao.com/v2/user/me", HttpMethod.GET, request,
			String.class);
		System.out.println("response = " + response);

		// Long id = response.getBody().id();
		// MemberJoinResponse memberJoinResponse = memberService.join("kakao", id.toString());
		MemberJoinResponse memberJoinResponse = new MemberJoinResponse(1L,
			MemberJoinResponse.LoginType.NEW_USER); // 컴파일 에러 안나게 하기위한 임시 코드
		return responseLoginSuccess(memberJoinResponse);
	}

	private OAuth2LoginResponse responseLoginSuccess(MemberJoinResponse memberJoinResponse) {
		String accessToken = jwtProvider.generateAccessToken(memberJoinResponse.id());
		String refreshToken = jwtProvider.generateRefreshToken(memberJoinResponse.id());
		return new OAuth2LoginResponse(accessToken, refreshToken, memberJoinResponse.loginType());
	}
}
