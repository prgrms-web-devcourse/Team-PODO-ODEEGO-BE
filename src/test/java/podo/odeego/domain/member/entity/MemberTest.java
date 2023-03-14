package podo.odeego.domain.member.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import podo.odeego.domain.member.exception.MemberNicknameOutOfBoundsException;
import podo.odeego.domain.member.exception.MemberNicknameUnformattedException;
import podo.odeego.global.error.exception.InvalidValueException;

class MemberTest {

	@ParameterizedTest
	@ValueSource(strings = {"넥네임", "닉넴1", "닉네에에에에에에에에에에에에에에에에에임", "닉네에에에에에에에에에에에에에에에에엠1"})
	@DisplayName("한글과 숫자만을 포함한 3~20 글자의 닉네임으로 입력하면 회원가입 추가정보를 입력에 성공합니다.")
	public void signUpSuccess(String actualNickname) {
		//given
		Member member = new Member("test", MemberType.PRE, "testProvider", "testProviderId");

		//when
		member.signUp(actualNickname, "테스트역");
		String expectedNickname = member.nickname();
		String expectedStationName = member.defaultStationName();

		//then
		assertThat(expectedNickname).isEqualTo(actualNickname);
		assertThat(expectedStationName).isEqualTo("테스트역");
	}

	@ParameterizedTest
	@ValueSource(strings = {"닉넴", "닉1", "닉네에에에에에에에에에에에에에에에에에에임", "닉네에에에에에에에에에에에에에에에에에임1"})
	@DisplayName("3글자 이상 20글자 이하가 아닌 길이의 닉네임을 입력할 경우 회원가입 추가정보 입력에 실패합니다.")
	public void signUpFailByNicknameBoundary(String wrongNickname) {
		//given
		Member member = new Member("test", MemberType.PRE, "testProvider", "testProviderId");

		//when & then
		int actualLength = wrongNickname.length();
		assertThatThrownBy(() -> member.signUp(wrongNickname, "테스트역"))
			.isInstanceOf(MemberNicknameOutOfBoundsException.class)
			.hasMessage(
				"Nickname Length of %s is not valid. It should be between '3' and '20'. But was '%d'.".formatted(
					wrongNickname, actualLength));
	}

	@ParameterizedTest
	@ValueSource(strings = {"닉네임1!", "닉네임a", "abe1", "abe!", "123!@", "ㅈㄷㅎㅈ", "ㄴ닉네임1"})
	@DisplayName("한글, 숫자 이외의 숫자가 들어올 경우 회원가입 추가정보 입력에 실패합니다.")
	public void signUpFailByNicknamePattern(String wrongNickname) {
		//given
		Member member = new Member("test", MemberType.PRE, "testProvider", "testProviderId");

		//when & then
		assertThatThrownBy(() -> member.signUp(wrongNickname, "테스트역"))
			.isInstanceOf(MemberNicknameUnformattedException.class)
			.hasMessage(
				"Nickname is invalid format. Input was %s".formatted(wrongNickname));
	}

	@Test
	@DisplayName("회원의 상태가 REGULAR 이면 회원가입 추가정보 입력에 실패합니다.")
	public void memberTypeShouldBePreWhenSignUp() {
		//given
		Member member = new Member("test", MemberType.REGULAR, "testProvider", "testProviderId");

		//when & then
		assertThatThrownBy(() -> member.signUp("닉네임123", "테스트역"))
			.isInstanceOf(MemberAlreadyRegularException.class)
			.hasMessage(
				"Can't signUp this member because member's type is %s".formatted(MemberType.REGULAR.toString()));
	}

	@Test
	@DisplayName("회원가입 추가정보 입력에 성공하면 회원 상태가 REGULAR 상태입니다.")
	public void memberTypeShouldBeRegularAfterSignUp() {
		//given
		Member member = new Member("test", MemberType.PRE, "testProvider", "testProviderId");

		//when
		member.signUp("닉네임123", "테스트역");
		MemberType actualMemberType = member.type();

		//then
		assertThat(actualMemberType).isEqualTo(MemberType.REGULAR);
	}

	@Test
	@DisplayName("회원가입 추가정보 입력에 실패하면 회원 상태가 PRE 상태입니다.")
	public void memberTypeShouldBePreIfSignUpFailed() {
		//given
		Member member = new Member("test", MemberType.PRE, "testProvider", "testProviderId");

		//when & then
		assertThatThrownBy(() -> member.signUp("닉", "테스트역"))
			.isInstanceOf(InvalidValueException.class)
			.hasMessage(
				"Nickname Length of %s is not valid. It should be between '3' and '20'. But was '%d'.".formatted(
					"닉", 1));
		MemberType actualMemberType = member.type();
		assertThat(actualMemberType).isEqualTo(MemberType.PRE);
	}
}