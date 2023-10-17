package podo.odeego.domain.refreshtoken.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;
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
		Long memberId = 1L;
		RefreshToken refreshToken = new RefreshToken(memberId, UUID.randomUUID().toString());

		//when
		refreshTokenRepository.save(refreshToken);

		//then
		Optional<RefreshToken> expectedRefreshToken = refreshTokenRepository.findByMemberId(memberId);
		assertThat(expectedRefreshToken).isPresent().get()
			.extracting("memberId", "token")
			.containsExactly(refreshToken.memberId(), refreshToken.token());
	}

	@Test
	@DisplayName("memberId key를 통해 RefreshToken을 조회할 수 있습니다.")
	void findByMemberId() {
		//given
		Long memberId = 1L;
		RefreshToken refreshToken = new RefreshToken(memberId, UUID.randomUUID().toString());
		refreshTokenRepository.save(refreshToken);

		//when
		Optional<RefreshToken> expectedRefreshToken = refreshTokenRepository.findByMemberId(memberId);

		//then
		assertThat(expectedRefreshToken).isPresent().get()
			.extracting("memberId", "token")
			.containsExactly(memberId, refreshToken.token());
	}

}