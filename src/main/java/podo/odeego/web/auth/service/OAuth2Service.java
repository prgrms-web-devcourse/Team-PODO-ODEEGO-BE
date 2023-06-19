package podo.odeego.web.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import podo.odeego.domain.account.social.service.SocialAccountService;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.service.MemberService;
import podo.odeego.infra.openapi.kakao.KakaoClient;
import podo.odeego.infra.openapi.kakao.dto.KakaoProfileResponse;
import podo.odeego.web.auth.dto.OAuth2LoginResponse;

@Service
public class OAuth2Service {

	private static final String PROVIDER = "kakao";

	private final Logger log = LoggerFactory.getLogger(OAuth2Service.class);

	private final KakaoClient kakaoClient;
	private final SocialAccountService socialAccountService;
	private final MemberService memberService;

	public OAuth2Service(
		KakaoClient kakaoClient,
		SocialAccountService socialAccountService,
		MemberService memberService) {
		this.kakaoClient = kakaoClient;
		this.socialAccountService = socialAccountService;
		this.memberService = memberService;
	}

	public OAuth2LoginResponse login(String oAuth2Token) {
		KakaoProfileResponse kakaoProfileResponse = kakaoClient.getUserInfo(oAuth2Token);

		return socialAccountService.findByProviderAndProviderId(PROVIDER, kakaoProfileResponse.providerId())
			.map(socialAuth -> {
				Member foundMember = memberService.findById(socialAuth.memberId());
				log.info("Already joined our service with OAuth2 account. provider: {}, memberType: {}",
					socialAuth.provider(), foundMember.type());
				return new OAuth2LoginResponse(foundMember.id(), kakaoProfileResponse.profileImageUrl(),
					foundMember.type());
			})
			.orElseGet(() -> join(kakaoProfileResponse));
	}

	private OAuth2LoginResponse join(KakaoProfileResponse kakaoProfileResponse) {
		Member joinedMember = memberService.join(kakaoProfileResponse.profileImageUrl());
		socialAccountService.save(PROVIDER, kakaoProfileResponse.providerId(), joinedMember.id());
		log.info("New member joined our service with OAuth2 account. provider: {}, memberType: {}", PROVIDER,
			joinedMember.type());
		return new OAuth2LoginResponse(joinedMember.id(), kakaoProfileResponse.profileImageUrl(),
			joinedMember.type());
	}
}
