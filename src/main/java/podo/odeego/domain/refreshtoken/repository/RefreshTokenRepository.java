package podo.odeego.domain.refreshtoken.repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import podo.odeego.domain.refreshtoken.entity.LegacyDtoRefreshToken;
import podo.odeego.domain.refreshtoken.entity.RefreshToken;

@Repository
public class RefreshTokenRepository {

	private final RedisTemplate<String, Long> template;
	private final long refreshTokenExpirationMillis;
	private final ObjectMapper objectMapper;

	public RefreshTokenRepository(
		@Value("${refresh-token.expiration}") long refreshTokenExpirationMillis,
		RedisTemplate<String, Long> template,
		ObjectMapper objectMapper) {
		this.refreshTokenExpirationMillis = refreshTokenExpirationMillis;
		this.template = template;
		this.objectMapper = objectMapper;
	}

	public void save(RefreshToken refreshToken, Long memberId) {
		String serializedRefreshToken;
		try {
			serializedRefreshToken = objectMapper.writeValueAsString(refreshToken);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		ValueOperations<String, Long> valueOperations = template.opsForValue();
		valueOperations.set(serializedRefreshToken, memberId);
		template.expire(serializedRefreshToken, refreshTokenExpirationMillis, TimeUnit.SECONDS);
	}

	public Optional<Long> findMemberIdByRefreshToken(RefreshToken refreshToken) {
		ValueOperations<String, Long> valueOperations = template.opsForValue();
		Long memberId;
		try {
			memberId = valueOperations.get(objectMapper.writeValueAsString(refreshToken));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		if (Objects.isNull(memberId)) {
			return Optional.empty();
		}
		return Optional.of(memberId);
	}

	//TODO: 컴파일 에러 방지
	public Optional<LegacyDtoRefreshToken> findById() {
		return Optional.empty();
	}

	// public Optional<LegacyDtoRefreshToken> findById(String refreshToken) {
	// 	ValueOperations<String, Long> valueOperations = template.opsForValue();
	// 	Long memberId = valueOperations.get(refreshToken);
	//
	// 	if (Objects.isNull(memberId)) {
	// 		return Optional.empty();
	// 	}
	// 	return Optional.of(LegacyDtoRefreshToken.of(refreshToken, memberId));
	// }
}
