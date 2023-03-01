package podo.odeego.web.api.auth.dto;

import podo.odeego.domain.member.entity.dto.MemberJoinResponse;

public record MemberLoginResponse(
	String accessToken,
	String refreshToken,
	MemberJoinResponse.LoginType loginType,
	String profileImageUrl
) {
}
