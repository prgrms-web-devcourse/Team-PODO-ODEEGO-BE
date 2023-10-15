package podo.odeego.domain.refreshtoken.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import podo.odeego.config.TestRedisConfig;
import podo.odeego.domain.refreshtoken.entity.RefreshToken;

@SpringBootTest(classes = TestRedisConfig.class)
class RefreshTokenRepositoryTest {

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Test
	@DisplayName("RefreshToken을 저장할 수 있습니다.")
	public void saveRefreshToken() {
		//given
		RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), true);
		Long memberId = 1L;

		//when
		refreshTokenRepository.save(refreshToken, memberId);
		Long actualMemberId = refreshTokenRepository.findMemberIdByRefreshToken(refreshToken).get();

		//then
		assertThat(actualMemberId).isEqualTo(1L);
	}
}