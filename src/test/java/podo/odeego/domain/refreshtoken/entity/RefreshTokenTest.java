package podo.odeego.domain.refreshtoken.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RefreshTokenTest {

	@Test
	@DisplayName("IsTokenEqualsTo() 메서드는 인자로 주어진 RefreshToken과 RefrsehToken 객체의 token 필드가 일치하면 true를 반환합니다.")
	void isTokenEqualsTo() {
		//given
		String token = UUID.randomUUID().toString();
		RefreshToken refreshToken = new RefreshToken(1L, token);

		//when
		boolean isTokenEqualsTo = refreshToken.isTokenEqualsTo(token);

		//then
		assertThat(isTokenEqualsTo).isTrue();
	}

	@Test
	@DisplayName("changeNewToken() 메서드는 인자로 주어진 RefreshToken을 통해 RefreshToken 객체의 token 값을 변경할 수 있습니다.")
	void changeNewToken() {
		//given
		RefreshToken refreshToken = new RefreshToken(1L, UUID.randomUUID().toString());
		String newToken = UUID.randomUUID().toString();

		//when
		refreshToken.changeNewToken(newToken);

		//then
		assertThat(refreshToken.token()).isEqualTo(newToken);
	}
}