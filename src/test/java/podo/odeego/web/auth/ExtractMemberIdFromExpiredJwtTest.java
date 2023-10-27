package podo.odeego.web.auth;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.common.base.Strings;

class ExtractMemberIdFromExpiredJwtTest {

	private final JwtProvider jwtProvider;

	ExtractMemberIdFromExpiredJwtTest() {
		this.jwtProvider = new JwtProvider(Strings.repeat("a", 32), 0);
	}

	@Test
	@DisplayName("만료된 JWT에서 MemberId를 추출할 수 있습니다.")
	void extractMemberIdFromExpiredJwt() {
		//given
		String accessToken = jwtProvider.generateAccessToken(1L);

		//when
		Long memberId = jwtProvider.extractMemberIdFromExpiredJwt(accessToken);

		//then
		assertThat(memberId).isEqualTo(1L);

	}

}