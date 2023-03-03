package podo.odeego.web.api.auth.dto;

import podo.odeego.domain.member.entity.MemberType;

public record MemberLoginResponse(
	String accessToken,
	String refreshToken,
	MemberType memberType,
	String profileImageUrl
) {
}
