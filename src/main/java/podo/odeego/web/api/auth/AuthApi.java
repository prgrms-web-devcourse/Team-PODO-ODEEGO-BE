package podo.odeego.web.api.auth;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import podo.odeego.domain.member.dto.MemberJoinResponse;
import podo.odeego.domain.member.service.MemberService;
import podo.odeego.web.api.auth.dto.GetMemberInfoResponse;
import podo.odeego.web.api.auth.dto.MemberLoginResponse;
import podo.odeego.web.api.auth.dto.OAuth2GetTokenResponse;
import podo.odeego.web.security.jwt.JwtProvider;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthApi {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final MemberService memberService;
	private final JwtProvider jwtProvider;
	private final String clientId;
	private final String frontLocalHost;
	private final String frontReleaseHost;

	public AuthApi(
		MemberService memberService,
		JwtProvider jwtProvider,
		@Value("${kakao.client.id}") String clientId,
		@Value("${server.host.front.local}") String frontLocalHost,
		@Value("${server.host.front.release}") String frontReleaseHost
	) {
		this.memberService = memberService;
		this.jwtProvider = jwtProvider;
		this.clientId = clientId;
		this.frontLocalHost = frontLocalHost;
		this.frontReleaseHost = frontReleaseHost;
	}

	@GetMapping("/login/oauth2/callback/kakao")
	public ResponseEntity<OAuth2GetTokenResponse> loginWithKakao(@RequestParam String code,
		HttpServletRequest httpServletRequest) {
		log.info("AuthApi.loginWithKakao() called");
		log.info("code = " + code);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Accept", "application/json");

		LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", "authorization_code");
		map.add("client_id", clientId);
		String requestUrl = httpServletRequest.getRequestURL().toString();
		if (requestUrl.startsWith(frontLocalHost)) {
			map.add("redirect_uri", "%s/kakao".formatted(frontLocalHost));
		} else {
			map.add("redirect_uri", "%s/kakao".formatted(frontReleaseHost));
		}
		map.add("code", code);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		ResponseEntity<OAuth2GetTokenResponse> response = restTemplate.postForEntity(
			"https://kauth.kakao.com/oauth/token",
			request, OAuth2GetTokenResponse.class);
		OAuth2GetTokenResponse body = response.getBody();

		return ResponseEntity.ok(body);
	}

	@PostMapping("/user/me")
	private MemberLoginResponse getMemberInfo(HttpServletRequest request) {
		log.info("AuthApi.getMemberInfo() called");
		log.info("request.getHeader(\"Authorization\") = " + request.getHeader("Authorization"));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Authorization", request.getHeader("Authorization"));

		HttpEntity<MultiValueMap<String, String>> apiRequest = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		ResponseEntity<GetMemberInfoResponse> response = restTemplate.exchange(
			"https://kapi.kakao.com/v2/user/me", HttpMethod.GET, apiRequest,
			GetMemberInfoResponse.class);
		String profileImageUrl = response.getBody().kakao_account().profile().profile_image_url();
		log.info("memberId = {}", response.getBody().id());
		log.info("profileImageUrl = {}", profileImageUrl);

		Long id = response.getBody().id();
		MemberJoinResponse memberJoinResponse = memberService.join("kakao", id.toString(), profileImageUrl);
		return responseLoginSuccess(memberJoinResponse, profileImageUrl);
	}

	private MemberLoginResponse responseLoginSuccess(MemberJoinResponse memberJoinResponse, String profileImageUrl) {
		String accessToken = jwtProvider.generateAccessToken(memberJoinResponse.id());
		String refreshToken = jwtProvider.generateRefreshToken(memberJoinResponse.id());
		return new MemberLoginResponse(accessToken, refreshToken, memberJoinResponse.memberType(), profileImageUrl);
	}
}
