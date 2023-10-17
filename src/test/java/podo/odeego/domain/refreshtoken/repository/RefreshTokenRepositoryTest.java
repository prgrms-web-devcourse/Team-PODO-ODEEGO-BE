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

	@Test
	@DisplayName("update하기 위해 인자로 넘긴 RefreshToken의 memberId가 저장소에 존재한다면 인자로 넘어온 RefreshToken으로 update할 수 있습니다.")
	void update() {
		//given
		Long memberId = 1L;
		RefreshToken refreshToken = new RefreshToken(memberId, UUID.randomUUID().toString());
		refreshTokenRepository.save(refreshToken);

		String newToken = UUID.randomUUID().toString();
		refreshToken.changeNewToken(newToken);

		//when
		refreshTokenRepository.update(refreshToken);

		//then
		Optional<RefreshToken> expectedRefreshToken = refreshTokenRepository.findByMemberId(memberId);
		assertThat(expectedRefreshToken).isPresent().get()
			.extracting("memberId", "token")
			.containsExactly(memberId, newToken);
	}

	@Test
	@DisplayName("update하기 위해 인자로 넘긴 RefreshToken의 memberId가 저장소에 존재하지 않는다면 인자로 넘어온 RefreshToken으로 update할 수 없습니다.")
	void updateFailByMemberId() {
		//given
		Long memberId = 1L;
		RefreshToken refreshToken = new RefreshToken(memberId, UUID.randomUUID().toString());
		refreshTokenRepository.save(refreshToken);

		Long wrongMemberId = 2L;
		RefreshToken refreshTokenWithWrongMemberId = new RefreshToken(wrongMemberId, UUID.randomUUID().toString());

		//when
		refreshTokenRepository.update(refreshTokenWithWrongMemberId);

		//then
		Optional<RefreshToken> expectedEmptyRefreshToken = refreshTokenRepository.findByMemberId(wrongMemberId);
		assertThat(expectedEmptyRefreshToken).isEmpty();
	}
}