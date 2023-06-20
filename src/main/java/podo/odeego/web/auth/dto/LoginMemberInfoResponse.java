package podo.odeego.web.auth.dto;

import podo.odeego.domain.member.entity.MemberType;

public record LoginMemberInfoResponse(
	Long memberId,
	String profileImageUrl,
	MemberType memberType
) {
}
