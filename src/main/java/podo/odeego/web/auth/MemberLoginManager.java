package podo.odeego.web.auth;

import org.springframework.stereotype.Component;

import podo.odeego.domain.auth.service.RefreshTokenService;
import podo.odeego.domain.member.dto.MemberJoinResponse;
import podo.odeego.web.api.auth.dto.MemberLoginResponse;

@Component
public class MemberLoginManager {

	private final MemberJoinManager memberJoinManager;
	private final RefreshTokenService refreshTokenService;
	private final JwtProvider jwtProvider;

	public MemberLoginManager(MemberJoinManager memberJoinManager, RefreshTokenService refreshTokenService,
		JwtProvider jwtProvider) {
		this.memberJoinManager = memberJoinManager;
		this.refreshTokenService = refreshTokenService;
		this.jwtProvider = jwtProvider;
	}

	public MemberLoginResponse login(String oAuth2Token) {
		MemberJoinResponse joinResponse = memberJoinManager.join(oAuth2Token);

		return MemberLoginResponse.of(
			jwtProvider.generateAccessToken(joinResponse.id()),
			refreshTokenService.save(joinResponse.id()),
			joinResponse
		);
	}
}
