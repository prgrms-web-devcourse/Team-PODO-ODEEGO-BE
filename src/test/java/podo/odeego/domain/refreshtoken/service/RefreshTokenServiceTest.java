package podo.odeego.domain.refreshtoken.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import podo.odeego.config.TestRedisConfig;
import podo.odeego.domain.refreshtoken.dto.RefreshTokenResponse;
import podo.odeego.domain.refreshtoken.entity.RefreshToken;
import podo.odeego.domain.refreshtoken.exception.RefreshTokenNotFoundException;
import podo.odeego.domain.refreshtoken.exception.WrongRefreshTokenException;
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

	@Test
	@DisplayName("가장 최근 RefreshToken과 memberId를 통해 새로운 RefreshToken을 로테이션시킬 수 있습니다.")
	void rotate() {
		//given
		Long memberId = 1L;
		String token = UUID.randomUUID().toString();
		refreshTokenRepository.save(new RefreshToken(memberId, token));

		//when
		RefreshTokenResponse response = refreshTokenService.rotate(memberId, token);

		//then
		assertThat(response.token()).isNotEqualTo(token);
	}

	@Test
	@DisplayName("memberId가 존재하지 않으면 새로운 RefreshToken 로테이션에 실패하고 예외가 발생합니다.")
	void rotateFailByMemberId() {
		//given
		Long memberId = 1L;
		String token = UUID.randomUUID().toString();
		refreshTokenRepository.save(new RefreshToken(memberId, token));

		Long wrongMemberId = 2L;

		//when & then
		assertThatThrownBy(() -> refreshTokenService.rotate(wrongMemberId, token))
			.isInstanceOf(RefreshTokenNotFoundException.class);
	}

	@Test
	@DisplayName("인자로 넘어간 RefreshToken과 memberId를 통해 조회한 RefreshToken과 일치하지 않으면 새로운 RefreshToken 로테이션에 실패하고 예외가 발생합니다.")
	void rotateFailByRefreshToken() {
		//given
		Long memberId = 1L;
		String token = UUID.randomUUID().toString();
		refreshTokenRepository.save(new RefreshToken(memberId, token));

		String wrongToken = UUID.randomUUID().toString();

		//when & then
		assertThatThrownBy(() -> refreshTokenService.rotate(memberId, wrongToken))
			.isInstanceOf(WrongRefreshTokenException.class);
	}
}