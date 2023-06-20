package podo.odeego.web.auth.dto;

import podo.odeego.domain.member.entity.MemberType;

public class LoginResponse {

	private String accessToken;
	private String refreshToken;
	private MemberType memberType;
	private String profileImageUrl;

	private LoginResponse() {
	}

	private LoginResponse(String accessToken, String refreshToken, MemberType memberType,
		String profileImageUrl) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.memberType = memberType;
		this.profileImageUrl = profileImageUrl;
	}

	public static LoginResponse of(String accessToken, String refreshToken, LoginMemberInfoResponse LoginMemberInfo) {
		return new LoginResponse(
			accessToken,
			refreshToken,
			LoginMemberInfo.memberType(),
			LoginMemberInfo.profileImageUrl()
		);
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public MemberType getMemberType() {
		return memberType;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}
}
