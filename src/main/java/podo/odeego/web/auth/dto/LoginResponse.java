package podo.odeego.web.auth.dto;

import podo.odeego.domain.member.entity.MemberType;

public class LoginResponse {

	private final String accessToken;
	private final String refreshToken;
	private final MemberType memberType;
	private final String profileImageUrl;

	private LoginResponse(String accessToken, String refreshToken, MemberType memberType,
		String profileImageUrl) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.memberType = memberType;
		this.profileImageUrl = profileImageUrl;
	}

	public static LoginResponse of(String accessToken, String refreshToken, OAuth2LoginResponse joinResponse) {
		return new LoginResponse(
			accessToken,
			refreshToken,
			joinResponse.memberType(),
			joinResponse.profileImageUrl()
		);
	}
}
