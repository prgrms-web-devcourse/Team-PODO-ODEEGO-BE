package podo.odeego.web.security.oauth2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import podo.odeego.domain.user.entity.Member;
import podo.odeego.domain.user.service.MemberService;

public class OAuth2AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final MemberService memberService;

	public OAuth2AuthenticationSuccessHandler(MemberService memberService) {
		this.memberService = memberService;
	}

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication
	) throws ServletException, IOException {
		if (authentication instanceof OAuth2AuthenticationToken token) {
			log.info("OAuth2Authentication Successed");
			OAuth2User oauth2User = token.getPrincipal();
			String provider = token.getAuthorizedClientRegistrationId();
			Member member = joinOAuth2UserToMember(oauth2User, provider);
		} else {
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}

	private Member joinOAuth2UserToMember(OAuth2User oauth2User, String provider) {
		return memberService.join(oauth2User, provider);
	}
}
