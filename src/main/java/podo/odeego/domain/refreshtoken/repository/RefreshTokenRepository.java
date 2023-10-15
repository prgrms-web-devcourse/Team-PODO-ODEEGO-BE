package podo.odeego.domain.refreshtoken.repository;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
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

	public String save(Long memberId) {
		ValueOperations<String, Long> valueOperations = template.opsForValue();

		// TODO
		// randomUUID로 Refresh TOken을 생성하기로 정책을 결정했다면, 그 정책을 수행하는 계층인 service에서 해줘야하는 것 아닌가?
		// 또한, valid 값을 초기에 true로 해야한다는 것도 정책(RTR)인데, service에서 해서 넘겨줘야지
		RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), true);
		String serializedRefreshToken;
		try {
			serializedRefreshToken = objectMapper.writeValueAsString(refreshToken);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		valueOperations.set(serializedRefreshToken, memberId);
		template.expire(serializedRefreshToken, refreshTokenExpirationMillis, TimeUnit.SECONDS);
		return refreshToken.getToken();
	}

	// public void save(LegacyDtoRefreshToken legacyDtoRefreshToken) {
	// 	ValueOperations<String, Long> valueOperations = template.opsForValue();
	// 	valueOperations.set(legacyDtoRefreshToken.token(), legacyDtoRefreshToken.memberId());
	// 	template.expire(legacyDtoRefreshToken.token(), refreshTokenExpirationMillis, TimeUnit.SECONDS);
	// }

	public Optional<Long> findMemberIdByRefreshToken(String refreshToken) {
		ValueOperations<String, Long> valueOperations = template.opsForValue();
		Long memberId;
		try {
			//TODO
			// key(RefreshToken)로 value(memberID)를 조회하는데, key 값이 항상 isValid가 true로 세팅되어야 하는 것은 service에서 알고있어야 하는 것 아닌가?
			memberId = valueOperations.get(objectMapper.writeValueAsString(new RefreshToken(refreshToken, true)));
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
