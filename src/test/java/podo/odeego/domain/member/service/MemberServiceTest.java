package podo.odeego.domain.member.service;

import static org.assertj.core.api.Assertions.*;

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
import podo.odeego.domain.station.entity.Station;
import podo.odeego.domain.station.repository.StationRepository;

@ExtendWith(SpringExtension.class)
@Import(TestConfig.class)
@DataJpaTest
class MemberServiceTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private MemberService memberService;

	@Test
	@DisplayName("서비스에 최초로 로그인한 사용자일 경우 준회원으로 회원가입됩니다")
	public void joinPreMember() {
		//given & when
		MemberJoinResponse joinedMember = memberService.join("testProvider", "1234", "testUrl");

		MemberType actualType = joinedMember.memberType();

		//then
		assertThat(actualType).isEqualTo(MemberType.PRE);
	}

	@Test
	@DisplayName("최초 로그인 후에 추가정보를 입력한 사용자의 경우 정회원으로 회원가입됩니다")
	public void joinRegularMember() {
		//given
		stationRepository.save(
			new Station("강남역", "서울특별시", 123.123, 123.123, "2호선"));
		MemberJoinResponse joinedMember = memberService.join("testProvider", "1234", "testUrl");

		//when
		MemberSignUpRequest signUpRequest = new MemberSignUpRequest("닉네임", "강남역");
		memberService.signUp(joinedMember.id(), signUpRequest);

		//then
		joinedMember = memberService.join("testProvider", "1234", "testUrl");
		MemberType actualType = joinedMember.memberType();
		assertThat(actualType).isEqualTo(MemberType.REGULAR);
	}

	@Test
	@DisplayName("중복된 닉네임으로 추가정보를 입력할 경우 실패합니다.")
	public void signUpFailedByNickname() {
		//given
		Member member = Member.ofNickname("중복된닉네임", "testProvider", "1234");
		Member existMember = memberRepository.save(member);

		//when
		MemberSignUpRequest signUpRequest = new MemberSignUpRequest("중복된닉네임", "강남역");

		//then
		assertThatThrownBy(() -> memberService.signUp(existMember.id(), signUpRequest))
			.isInstanceOf(MemberNicknameDuplicatedException.class)
			.hasMessage("Cannot sig up with duplicated nickname: %s".formatted("중복된닉네임"));
	}

	@Test
	@DisplayName("DB상에 존재하지 않는 역을 추가정보로 입력한 경우 실패합니다")
	public void signUpFailedByDefaultStationName() {
		//given
		Member member = Member.ofNickname("닉네임", "testProvider", "1234");
		Member existMember = memberRepository.save(member);

		//when
		MemberSignUpRequest signUpRequest = new MemberSignUpRequest("닉네임", "없는역없는역");

		//then
		assertThatThrownBy(() -> memberService.signUp(existMember.id(), signUpRequest))
			.isInstanceOf(MemberNicknameDuplicatedException.class)
			.hasMessage("Cannot sig up with duplicated nickname: %s".formatted("닉네임"));
	}
}