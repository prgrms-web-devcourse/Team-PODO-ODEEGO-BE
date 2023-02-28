package podo.odeego.web.security.oauth2;

import podo.odeego.domain.member.entity.dto.MemberJoinResponse;

public record OAuth2LoginResponse(
	String accessToken,
	String refreshToken,
	MemberJoinResponse.LoginType loginType
) {
}
