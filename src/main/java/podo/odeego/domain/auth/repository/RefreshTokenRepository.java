package podo.odeego.domain.auth.repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import podo.odeego.domain.auth.entity.RefreshToken;

@Repository
public class RefreshTokenRepository {

	private final RedisTemplate<String, Long> template;

	public RefreshTokenRepository(RedisTemplate<String, Long> template) {
		this.template = template;
	}

	public void save(RefreshToken refreshToken) {
		ValueOperations<String, Long> valueOperations = template.opsForValue();
		valueOperations.set(refreshToken.token(), refreshToken.memberId());
		template.expire(refreshToken.token(), 60L, TimeUnit.SECONDS);
	}

	public Optional<RefreshToken> findById(String refreshToken) {
		ValueOperations<String, Long> valueOperations = template.opsForValue();
		Long memberId = valueOperations.get(refreshToken);

		if (Objects.isNull(memberId)) {
			return Optional.empty();
		}
		return Optional.of(RefreshToken.of(refreshToken, memberId));
	}
}
