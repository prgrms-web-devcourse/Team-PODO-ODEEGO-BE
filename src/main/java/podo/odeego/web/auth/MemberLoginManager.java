package podo.odeego.web.auth;

import org.springframework.stereotype.Component;

import podo.odeego.domain.member.dto.MemberJoinResponse;
import podo.odeego.web.api.auth.dto.MemberLoginResponse;

@Component
public class MemberLoginManager {

	private final MemberJoinManager memberJoinManager;
	private final JwtProvider jwtProvider;

	public MemberLoginManager(MemberJoinManager memberJoinManager, JwtProvider jwtProvider) {
		this.memberJoinManager = memberJoinManager;
		this.jwtProvider = jwtProvider;
	}

	public MemberLoginResponse login(String oAuth2Token) {
		MemberJoinResponse joinResponse = memberJoinManager.join(oAuth2Token);

		return MemberLoginResponse.of(
			jwtProvider.generateToken(joinResponse.id()),
			joinResponse
		);
	}
}
