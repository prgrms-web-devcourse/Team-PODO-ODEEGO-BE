package podo.odeego.web.api.auth.dto;

import podo.odeego.domain.member.dto.MemberJoinResponse;
import podo.odeego.domain.member.entity.MemberType;
import podo.odeego.web.security.jwt.dto.GenerateTokenResponse;

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

	public static MemberLoginResponse of(GenerateTokenResponse tokenResponse, MemberJoinResponse joinResponse){
		return new MemberLoginResponse(
			tokenResponse.accessToken(),
			tokenResponse.refreshToken(),
			joinResponse.memberType(),
			joinResponse.profileImageUrl()
		);
	}
}
