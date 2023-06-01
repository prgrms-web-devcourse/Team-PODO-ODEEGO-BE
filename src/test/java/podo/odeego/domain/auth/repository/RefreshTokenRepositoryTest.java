package podo.odeego.domain.auth.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import podo.odeego.config.TestRedisConfig;
import podo.odeego.domain.auth.entity.RefreshToken;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestRedisConfig.class)
class RefreshTokenRepositoryTest {

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Test
	@DisplayName("RefreshToken을 저장할 수 있습니다.")
	public void saveRefreshToken() {
		String actualUUID = UUID.randomUUID().toString();
		RefreshToken refreshToken = RefreshToken.of(actualUUID, 1L);
		refreshTokenRepository.save(refreshToken);
		String expectedUUID = refreshTokenRepository.findById(refreshToken.token())
			.get().token();
		assertThat(expectedUUID).isEqualTo(actualUUID);
	}
}