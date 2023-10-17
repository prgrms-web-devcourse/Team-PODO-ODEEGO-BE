package podo.odeego.domain.refreshtoken.repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import podo.odeego.domain.refreshtoken.entity.RefreshToken;

@Repository
public class RefreshTokenRepository {

	private final RedisTemplate<Long, String> template;
	private final long refreshTokenExpirationMillis;
	private final ObjectMapper objectMapper;

	public RefreshTokenRepository(
		@Value("${refresh-token.expiration}") long refreshTokenExpirationMillis,
		RedisTemplate<Long, String> template,
		ObjectMapper objectMapper) {
		this.refreshTokenExpirationMillis = refreshTokenExpirationMillis;
		this.template = template;
		this.objectMapper = objectMapper;
	}

	public void save(RefreshToken refreshToken) {
		ValueOperations<Long, String> valueOperations = template.opsForValue();
		valueOperations.set(refreshToken.memberId(), refreshToken.token());
		template.expire(refreshToken.memberId(), refreshTokenExpirationMillis, TimeUnit.SECONDS);
	}
	// public void save(RefreshToken refreshToken, Long memberId) {
	// 	String serializedRefreshToken;
	// 	try {
	// 		serializedRefreshToken = objectMapper.writeValueAsString(refreshToken);
	// 	} catch (JsonProcessingException e) {
	// 		throw new RuntimeException(e);
	// 	}
	//
	// 	ValueOperations<String, Long> valueOperations = template.opsForValue();
	// 	valueOperations.set(serializedRefreshToken, memberId);
	// 	template.expire(serializedRefreshToken, refreshTokenExpirationMillis, TimeUnit.SECONDS);
	// }

	public Optional<RefreshToken> findByMemberId(Long memberId) {
		ValueOperations<Long, String> valueOperations = template.opsForValue();
		String refreshToken = valueOperations.get(memberId);

		if (Objects.isNull(refreshToken)) {
			return Optional.empty();
		}
		return Optional.of(new RefreshToken(memberId, refreshToken));
	}

	// public Optional<Long> findMemberIdByRefreshToken(RefreshToken refreshToken) {
	// 	ValueOperations<String, Long> valueOperations = template.opsForValue();
	// 	Long memberId;
	// 	try {
	// 		memberId = valueOperations.get(objectMapper.writeValueAsString(refreshToken));
	// 	} catch (JsonProcessingException e) {
	// 		throw new RuntimeException(e);
	// 	}
	//
	// 	if (Objects.isNull(memberId)) {
	// 		return Optional.empty();
	// 	}
	// 	return Optional.of(memberId);
	// }

	// public void updateRefreshToken(RefreshToken oldrefreshToken, RefreshToken newRefreshToken) {
	// 	String serializedOldRefreshToken, serializedNewRefreshToken;
	// 	try {
	// 		serializedOldRefreshToken = objectMapper.writeValueAsString(oldrefreshToken);
	// 		serializedNewRefreshToken = objectMapper.writeValueAsString(newRefreshToken);
	// 	} catch (JsonProcessingException e) {
	// 		throw new RuntimeException(e);
	// 	}
	// 	template.rename(serializedOldRefreshToken, serializedNewRefreshToken);
	// }

	//TODO(10.16): 컴파일 에러 방지
	public Optional<RefreshToken> findById() {
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
