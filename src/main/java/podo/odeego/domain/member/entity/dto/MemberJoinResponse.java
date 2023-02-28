package podo.odeego.domain.member.entity.dto;

public record MemberJoinResponse(
	Long id,
	LoginType loginType
) {

	public static MemberJoinResponse newMember(Long memberId) {
		return new MemberJoinResponse(memberId, LoginType.NEW_USER);
	}

	public static MemberJoinResponse existMember(Long memberId) {
		return new MemberJoinResponse(memberId, LoginType.EXIST_USER);
	}

	public enum LoginType {
		NEW_USER, EXIST_USER
	}
}
