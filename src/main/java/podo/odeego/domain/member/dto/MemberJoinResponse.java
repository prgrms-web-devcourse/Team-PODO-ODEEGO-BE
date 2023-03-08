package podo.odeego.domain.member.dto;

import podo.odeego.domain.member.entity.MemberType;

public record MemberJoinResponse(
	Long id,
	String profileImageUrl,
	MemberType memberType
) {
}
