package podo.odeego.domain.auth.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import podo.odeego.config.TestRedisConfig;
import podo.odeego.domain.refreshtoken.entity.RefreshToken;
import podo.odeego.domain.refreshtoken.repository.RefreshTokenRepository;

@SpringBootTest(classes = TestRedisConfig.class)
class RefreshTokenRepositoryTest {

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Test
	@DisplayName("RefreshToken을 저장할 수 있습니다.")
	public void saveRefreshToken() {
		//given
		String actualUUID = UUID.randomUUID().toString();

		//when
		RefreshToken refreshToken = RefreshToken.of(actualUUID, 1L);
		refreshTokenRepository.save(refreshToken);
		String expectedUUID = refreshTokenRepository.findById(refreshToken.token())
			.get().token();

		//then
		assertThat(expectedUUID).isEqualTo(actualUUID);
	}
}