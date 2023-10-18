package podo.odeego.domain.refreshtoken.repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import podo.odeego.domain.refreshtoken.entity.RefreshToken;

@Repository
public class RefreshTokenRepository {

	private final RedisTemplate<Long, String> redisTemplate;
	private final long refreshTokenExpirationMillis;

	public RefreshTokenRepository(
		@Value("${refresh-token.expiration}") long refreshTokenExpirationMillis,
		RedisTemplate<Long, String> redisTemplate) {
		this.refreshTokenExpirationMillis = refreshTokenExpirationMillis;
		this.redisTemplate = redisTemplate;
	}

	public void save(RefreshToken refreshToken) {
		ValueOperations<Long, String> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(refreshToken.memberId(), refreshToken.token());
		redisTemplate.expire(refreshToken.memberId(), refreshTokenExpirationMillis, TimeUnit.MILLISECONDS);
	}

	public void update(RefreshToken refreshToken) {
		ValueOperations<Long, String> valueOperations = redisTemplate.opsForValue();
		valueOperations.setIfPresent(refreshToken.memberId(), refreshToken.token(),
			refreshTokenExpirationMillis, TimeUnit.MILLISECONDS);
	}

	public void deleteByMemberId(Long memberId) {
		redisTemplate.delete(memberId);
	}

	public Optional<RefreshToken> findByMemberId(Long memberId) {
		ValueOperations<Long, String> valueOperations = redisTemplate.opsForValue();
		String refreshToken = valueOperations.get(memberId);

		if (Objects.isNull(refreshToken)) {
			return Optional.empty();
		}
		return Optional.of(new RefreshToken(memberId, refreshToken));
	}
}
