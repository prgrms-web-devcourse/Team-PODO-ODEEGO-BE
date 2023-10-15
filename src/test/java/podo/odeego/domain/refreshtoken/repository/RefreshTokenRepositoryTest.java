package podo.odeego.domain.refreshtoken.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import podo.odeego.config.TestRedisConfig;

@SpringBootTest(classes = TestRedisConfig.class)
class RefreshTokenRepositoryTest {

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Test
	@DisplayName("RefreshToken을 저장할 수 있습니다.")
	public void saveRefreshToken() {
		//given
		// String actualUUID = UUID.randomUUID().toString();

		//when
		// LegacyDtoRefreshToken legacyDtoRefreshToken = LegacyDtoRefreshToken.of(actualUUID, 1L);\
		String refreshToken = refreshTokenRepository.save(1L);
		Long memberId = refreshTokenRepository.findMemberIdByRefreshToken(refreshToken)
			.get();

		//then
		assertThat(memberId).isEqualTo(1L);
	}
}