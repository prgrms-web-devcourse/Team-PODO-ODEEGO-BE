package podo.odeego.domain.member.dto;

public record MemberJoinRes(
	Long id,
	LoginType loginType
) {

	public static MemberJoinRes newMember(Long memberId) {
		return new MemberJoinRes(memberId, LoginType.NEW_USER);
	}

	public static MemberJoinRes existMember(Long memberId) {
		return new MemberJoinRes(memberId, LoginType.EXIST_USER);
	}

	public enum LoginType {
		NEW_USER, EXIST_USER
	}
}
