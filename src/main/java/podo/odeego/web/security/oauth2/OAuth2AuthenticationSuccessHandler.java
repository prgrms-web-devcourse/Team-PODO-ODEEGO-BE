package podo.odeego.web.security.oauth2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import podo.odeego.domain.member.entity.dto.MemberJoinResponse;
import podo.odeego.domain.member.service.MemberService;
import podo.odeego.web.security.jwt.JwtProvider;

public class OAuth2AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final MemberService memberService;
	private final JwtProvider jwtProvider;
	private final ObjectMapper objectMapper;

	public OAuth2AuthenticationSuccessHandler(MemberService memberService, JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
		this.memberService = memberService;
		this.objectMapper = new ObjectMapper();
	}

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication
	) throws ServletException, IOException {
		if (authentication instanceof OAuth2AuthenticationToken token) {
			log.info("OAuth2Authentication Success");
			OAuth2User oauth2User = token.getPrincipal();
			MemberJoinResponse memberJoinResponse = joinOAuth2UserToMember(
				token.getAuthorizedClientRegistrationId(),
				oauth2User.getName()
			);
			responseLoginSuccess(response, memberJoinResponse);
		} else {
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}

	private MemberJoinResponse joinOAuth2UserToMember(String provider, String providerId) {
		return memberService.join(provider, providerId);
	}

	private void responseLoginSuccess(HttpServletResponse response, MemberJoinResponse memberJoinResponse) throws
		IOException {
		String accessToken = jwtProvider.generateAccessToken(memberJoinResponse.id());
		String refreshToken = jwtProvider.generateRefreshToken(memberJoinResponse.id());

		response.setContentType("application/json");
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.getWriter().write(objectMapper.writeValueAsString(
			new OAuth2LoginResponse(accessToken, refreshToken, memberJoinResponse.loginType())
		));
	}
}
