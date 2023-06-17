package podo.odeego.web.auth.dto;

import podo.odeego.domain.member.entity.MemberType;

public record OAuth2LoginResponse(
	Long id,
	String profileImageUrl,
	MemberType memberType
) {
}
