package podo.odeego.domain.member.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import podo.odeego.config.TestConfig;
import podo.odeego.domain.member.dto.MemberJoinResponse;
import podo.odeego.domain.member.dto.MemberSignUpRequest;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.entity.MemberType;
import podo.odeego.domain.member.exception.MemberNicknameDuplicatedException;
import podo.odeego.domain.member.repository.MemberRepository;

@ExtendWith(SpringExtension.class)
@Import(TestConfig.class)
@DataJpaTest
class MemberServiceTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MemberService memberService;

	@Test
	@DisplayName("서비스에 최초로 로그인한 사용자일 경우 준회원으로 회원가입됩니다")
	public void joinPreMember() {
		//given & when
		MemberJoinResponse joinedMember = memberService.join("testProvider", "1234", "testUrl");

		MemberType actualType = joinedMember.memberType();

		//then
		Assertions.assertThat(actualType).isEqualTo(MemberType.PRE);
	}

	@Test
	@DisplayName("최초 로그인 후에 추가정보를 입력한 사용자의 경우 정회원으로 회원가입됩니다")
	public void joinRegularMember() {
		//given
		MemberJoinResponse joinedMember = memberService.join("testProvider", "1234", "testUrl");

		//when
		MemberSignUpRequest signUpRequest = new MemberSignUpRequest("nickname", "강남역");
		memberService.signUp(joinedMember.id(), signUpRequest);

		//then
		joinedMember = memberService.join("testProvider", "1234", "testUrl");
		MemberType actualType = joinedMember.memberType();
		Assertions.assertThat(actualType).isEqualTo(MemberType.REGULAR);
	}

	@Test
	@DisplayName("중복된 닉네임으로 추가정보를 입력할 경우 실패합니다.")
	public void signUpFailedByNickname() {
		//given
		Member member = Member.ofNickname("duplicatedNickname", "testProvider", "1234");
		Member existMember = memberRepository.save(member);

		//when
		MemberSignUpRequest signUpRequest = new MemberSignUpRequest("duplicatedNickname", "강남역");

		//then
		Assertions.assertThatThrownBy(() -> memberService.signUp(existMember.id(), signUpRequest))
			.isInstanceOf(MemberNicknameDuplicatedException.class)
			.hasMessage("Cannot sig up with duplicated nickname: %s".formatted("duplicatedNickname"));
	}
}