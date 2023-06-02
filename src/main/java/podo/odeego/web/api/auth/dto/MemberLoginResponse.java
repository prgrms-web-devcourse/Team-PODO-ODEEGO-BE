package podo.odeego.web.api.auth.dto;

import podo.odeego.domain.member.dto.MemberJoinResponse;
import podo.odeego.domain.member.entity.MemberType;
import podo.odeego.web.auth.dto.GenerateTokenResponse;

public class MemberLoginResponse {
	private final String accessToken;
	private final String refreshToken;
	private final MemberType memberType;
	private final String profileImageUrl;

	private MemberLoginResponse(String accessToken, String refreshToken, MemberType memberType, String profileImageUrl) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.memberType = memberType;
		this.profileImageUrl = profileImageUrl;
	}

	public static MemberLoginResponse of(GenerateTokenResponse tokenResponse, MemberJoinResponse joinResponse) {
		return new MemberLoginResponse(
			tokenResponse.accessToken(),
			tokenResponse.refreshToken(),
			joinResponse.memberType(),
			joinResponse.profileImageUrl()
		);
	}

	public static MemberLoginResponse of(String accessToken, String refreshToken, MemberJoinResponse joinResponse) {
		return new MemberLoginResponse(
			accessToken,
			refreshToken,
			joinResponse.memberType(),
			joinResponse.profileImageUrl()
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
