package podo.odeego.web.security.oauth2;

import podo.odeego.domain.member.dto.MemberJoinRes;

public record OAuth2LoginRes(
	String accessToken,
	String refreshToken,
	MemberJoinRes.LoginType loginType
) {
}
