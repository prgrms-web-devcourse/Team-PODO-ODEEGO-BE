package podo.odeego.domain.refreshtoken.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RefreshTokenTest {

	@Test
	@DisplayName("인자로 주어진 RefreshToken과 RefrsehToken 객체의 token 필드가 일치하면 true를 반환합니다.")
	void isTokenEqualsTo() {
		//given
		String token = UUID.randomUUID().toString();
		RefreshToken refreshToken = new RefreshToken(1L, token);

		//when
		boolean isTokenEqualsTo = refreshToken.isTokenEqualsTo(token);

		//then
		assertThat(isTokenEqualsTo).isTrue();
	}
}