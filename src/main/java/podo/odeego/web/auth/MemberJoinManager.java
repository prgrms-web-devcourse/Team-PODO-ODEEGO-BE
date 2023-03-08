package podo.odeego.web.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import podo.odeego.domain.member.dto.MemberJoinResponse;
import podo.odeego.domain.member.service.MemberService;
import podo.odeego.infra.openapi.kakao.KakaoClient;
import podo.odeego.infra.openapi.kakao.dto.KakaoProfileResponse;

@Component
public class MemberJoinManager {

	private final KakaoClient kakaoClient;
	private final MemberService memberService;
	private final String provider;

	public MemberJoinManager(
		KakaoClient kakaoClient,
		MemberService memberService,
		@Value("${oauth2.client.provider.name}") String provider
	) {
		this.kakaoClient = kakaoClient;
		this.memberService = memberService;
		this.provider = provider;
	}

	public MemberJoinResponse join(String oAuth2Token) {
		return joinByKakaoProfile(getUserInfoFromKakao(oAuth2Token));
	}

	private MemberJoinResponse joinByKakaoProfile(KakaoProfileResponse kakaoProfile) {
		return memberService.join(
			kakaoProfile.profileImageUrl(),
			provider,
			kakaoProfile.providerId().toString());
	}

	private KakaoProfileResponse getUserInfoFromKakao(String oAuth2Token) {
		return kakaoClient.getUserInfo(oAuth2Token);
	}
}
