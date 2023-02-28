package podo.odeego.web.api.auth;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import podo.odeego.domain.member.dto.MemberJoinResponse;
import podo.odeego.domain.member.service.MemberService;
import podo.odeego.web.api.auth.dto.GetMemberInfoResponse;
import podo.odeego.web.api.auth.dto.OAuth2GetTokenResponse;
import podo.odeego.web.security.jwt.JwtProvider;
import podo.odeego.web.security.oauth2.OAuth2LoginResponse;

@RestController
public class AuthApi {

	private final MemberService memberService;
	private final JwtProvider jwtProvider;

	public AuthApi(MemberService memberService, JwtProvider jwtProvider) {
		this.memberService = memberService;
		this.jwtProvider = jwtProvider;
	}

	@GetMapping("/api/v1/auth/login/oauth2/callback/kakao")
	public ResponseEntity<OAuth2GetTokenResponse> loginWithKakao(@RequestParam String code) {
		System.out.println("AuthApi.loginWithKakao called at " + LocalDateTime.now());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Accept", "application/json");
		System.out.println("code = " + code);

		LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", "authorization_code");
		map.add("client_id", "06d31e08cffa1b28d94af0313467cde8");
		map.add("redirect_uri", "http://localhost:3000/kakao");
		map.add("code", code);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		ResponseEntity<OAuth2GetTokenResponse> response = restTemplate.postForEntity(
			"https://kauth.kakao.com/oauth/token",
			request, OAuth2GetTokenResponse.class);
		OAuth2GetTokenResponse body = response.getBody();

		System.out.println("================AuthApi.loginWithKakao END================================");
		return ResponseEntity.ok(body);
	}

	@PostMapping("/api/v1/auth/user/me")
	private OAuth2LoginResponse getMemberInfo(HttpServletRequest request) {
		System.out.println("AuthApi.getMemberInfo");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		System.out.println("request.getHeader(\"Authhorization\") = " + request.getHeader("Authorization"));
		headers.add("Authorization", request.getHeader("Authorization"));

		HttpEntity<MultiValueMap<String, String>> apiRequest = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		ResponseEntity<GetMemberInfoResponse> response = restTemplate.exchange(
			"https://kapi.kakao.com/v2/user/me", HttpMethod.GET, apiRequest,
			GetMemberInfoResponse.class);
		System.out.println("response = " + response.getBody().id());

		Long id = response.getBody().id();
		MemberJoinResponse memberJoinResponse = memberService.join("kakao", id.toString());

		System.out.println("================AuthApi.getMemberInfo END================================");
		return responseLoginSuccess(memberJoinResponse);
	}

	private OAuth2LoginResponse responseLoginSuccess(MemberJoinResponse memberJoinResponse) {
		String accessToken = jwtProvider.generateAccessToken(memberJoinResponse.id());
		String refreshToken = jwtProvider.generateRefreshToken(memberJoinResponse.id());
		return new OAuth2LoginResponse(accessToken, refreshToken, memberJoinResponse.loginType());
	}
}
