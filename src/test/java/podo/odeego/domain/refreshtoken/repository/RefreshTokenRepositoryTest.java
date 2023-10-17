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

	// @Test
	// @DisplayName("RefreshToken 객체의 필드값과 일치하는 데이터가 존재한다면 memberId를 조회할 수 있습니다.")
	// void findMemberIdByRefreshToken() {
	// 	//given
	// 	RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), true);
	// 	Long memberId = 1L;
	// 	refreshTokenRepository.save(refreshToken, memberId);
	//
	// 	//when
	// 	Optional<Long> actualMemberId = refreshTokenRepository.findMemberIdByRefreshToken(refreshToken);
	//
	// 	//then
	// 	assertThat(actualMemberId).isPresent().get()
	// 		.isEqualTo(1L);
	// }
	//
	// @Test
	// @DisplayName("RefreshToken 객체의 isValid 필드는 일치하지만 token 필드가 일치하지 않으면 memberId를 조회할 수 없습니다.")
	// void findMemberIdByRefreshTokenFailByToken() {
	// 	//given
	// 	RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), true);
	// 	Long memberId = 1L;
	// 	refreshTokenRepository.save(refreshToken, memberId);
	//
	// 	RefreshToken notExistRefreshToken = new RefreshToken(UUID.randomUUID().toString(), true);
	//
	// 	//when
	// 	Optional<Long> actualMemberId = refreshTokenRepository.findMemberIdByRefreshToken(notExistRefreshToken);
	//
	// 	//then
	// 	assertThat(actualMemberId).isEmpty();
	// }

	// @Test
	// @DisplayName("RefreshToken 객체의 token 필드는 일치하지만 isValid 필드가 일치하지 않으면 memberId를 조회할 수 없습니다.")
	// void findMemberIdByRefreshTokenFailByIsValid() {
	// 	//given
	// 	RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), true);
	// 	Long memberId = 1L;
	// 	refreshTokenRepository.save(refreshToken, memberId);
	//
	// 	RefreshToken notExistRefreshToken = new RefreshToken(refreshToken.getToken(), false);
	//
	// 	//when
	// 	Optional<Long> actualMemberId = refreshTokenRepository.findMemberIdByRefreshToken(notExistRefreshToken);
	//
	// 	//then
	// 	assertThat(actualMemberId).isEmpty();
	// }
	//
	// @Test
	// @DisplayName("RefreshToken을 새로운 RefreshToken으로 갱신할 수 있습니다.")
	// void updateRefreshToken() {
	// 	//given
	// 	RefreshToken oldRefreshToken = new RefreshToken(UUID.randomUUID().toString(), true);
	// 	refreshTokenRepository.save(oldRefreshToken, 1L);
	//
	// 	RefreshToken newRefreshToken = new RefreshToken(UUID.randomUUID().toString(), true);
	//
	// 	//when
	// 	refreshTokenRepository.updateRefreshToken(oldRefreshToken, newRefreshToken);
	//
	// 	//then
	// 	Optional<Long> notExistsMemberId = refreshTokenRepository.findMemberIdByRefreshToken(oldRefreshToken);
	// 	assertThat(notExistsMemberId).isEmpty();
	//
	// 	Optional<Long> actualMemberId = refreshTokenRepository.findMemberIdByRefreshToken(newRefreshToken);
	// 	assertThat(actualMemberId).isPresent().get()
	// 		.isEqualTo(1L);
	// }
}