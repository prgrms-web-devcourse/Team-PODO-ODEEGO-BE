package podo.odeego.web.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import podo.odeego.domain.member.dto.MemberJoinResponse;
import podo.odeego.domain.member.service.MemberService;
import podo.odeego.infra.openapi.kakao.KakaoClient;
import podo.odeego.infra.openapi.kakao.dto.KakaoProfileResponse;
import podo.odeego.web.api.auth.dto.MemberLoginResponse;
import podo.odeego.web.security.jwt.JwtProvider;

@Component
public class AuthorizationComponent {

	private final KakaoClient kakaoClient;
	private final MemberService memberService;
	private final JwtProvider jwtProvider;
	private final String provider;

	public AuthorizationComponent(
		KakaoClient kakaoClient,
		MemberService memberService,
		JwtProvider jwtProvider,
		@Value("${oauth2.client.provider.name}") String provider
	) {
		this.kakaoClient = kakaoClient;
		this.memberService = memberService;
		this.jwtProvider = jwtProvider;
		this.provider = provider;
	}

	public MemberLoginResponse getMemberInfo(String oAuth2Token) {
		KakaoProfileResponse kakaoProfile = kakaoClient.getUserInfo(oAuth2Token);
		MemberJoinResponse joinResponse = memberService.join(
			kakaoProfile.profileImageUrl(),
			provider,
			kakaoProfile.providerId().toString());

		return MemberLoginResponse.of(
			jwtProvider.generateToken(joinResponse.id()),
			joinResponse
		);
	}
}
