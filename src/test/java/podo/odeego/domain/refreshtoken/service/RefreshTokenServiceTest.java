package podo.odeego.domain.refreshtoken.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import podo.odeego.config.TestRedisConfig;
import podo.odeego.domain.refreshtoken.dto.RefreshTokenResponse;
import podo.odeego.domain.refreshtoken.entity.RefreshToken;
import podo.odeego.domain.refreshtoken.repository.RefreshTokenRepository;

@SpringBootTest(classes = TestRedisConfig.class)
class RefreshTokenServiceTest {

	@Autowired
	private RefreshTokenService refreshTokenService;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Test
	@DisplayName("memberId를 인자로 받으면 RefreshToken이 저장됩니다.")
	void createAndSaveRefreshToken() {
		//given
		Long memberId = 1L;

		//when
		refreshTokenService.create(memberId);

		//then
		Optional<RefreshToken> expectedRefreshToken = refreshTokenRepository.findByMemberId(memberId);
		assertThat(expectedRefreshToken).isPresent();
	}

	@Test
	@DisplayName("memberId를 인자로 받으면 RefreshTokenResponse 객체가 return됩니다.")
	void createAndReturnRefreshToken() {
		//given
		Long memberId = 1L;

		//when
		RefreshTokenResponse response = refreshTokenService.create(memberId);

		//then
		assertThat(response.token()).isNotBlank();
	}
}