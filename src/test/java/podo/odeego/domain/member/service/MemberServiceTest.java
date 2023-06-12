package podo.odeego.domain.member.service;

import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import podo.odeego.config.TestConfig;
import podo.odeego.domain.group.entity.Group;
import podo.odeego.domain.group.entity.GroupCapacity;
import podo.odeego.domain.group.entity.GroupMember;
import podo.odeego.domain.group.entity.ParticipantType;
import podo.odeego.domain.group.repository.GroupMemberRepository;
import podo.odeego.domain.group.repository.GroupRepository;
import podo.odeego.domain.member.dto.MemberJoinResponse;
import podo.odeego.domain.member.dto.MemberSignUpRequest;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.entity.MemberType;
import podo.odeego.domain.member.exception.MemberNicknameDuplicatedException;
import podo.odeego.domain.member.exception.MemberNotFoundException;
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
	private GroupRepository groupRepository;

	@Autowired
	private GroupMemberRepository groupMemberRepository;

	@Autowired
	private MemberService memberService;

	@Autowired
	private EntityManager em;

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
			new Station("강남역", 123.123, 123.123, "2호선"));
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
		Member member = Member.ofNickname("중복된닉네임");
		Member existMember = memberRepository.save(member);

		//when
		MemberSignUpRequest signUpRequest = new MemberSignUpRequest("중복된닉네임", "강남역");

		//then
		assertThatThrownBy(() -> memberService.signUp(existMember.id(), signUpRequest))
			.isInstanceOf(MemberNicknameDuplicatedException.class)
			.hasMessage("Cannot sign up with duplicated nickname: %s".formatted("중복된닉네임"));
	}

	@Test
	@DisplayName("올바른 회원 ID로 회원 탈퇴를 할 경우 성공합니다.")
	public void leaveSuccess() {
		//given
		stationRepository.save(
			new Station("강남역", 123.123, 123.123, "2호선"));
		MemberJoinResponse joinedMember = memberService.join("testProvider", "1234", "testUrl");
		MemberSignUpRequest signUpRequest = new MemberSignUpRequest("닉네임", "강남역");
		memberService.signUp(joinedMember.id(), signUpRequest);

		//when
		memberService.leave(joinedMember.id());

		//then
		int actual = memberRepository.findAll().size();
		assertThat(actual).isEqualTo(0);
	}

	@Test
	@DisplayName("존재하지 않는 회원 ID로 회원 탈퇴를 할 경우 실패합니다.")
	public void leaveFailedById() {
		//given
		stationRepository.save(
			new Station("강남역", 123.123, 123.123, "2호선"));
		MemberJoinResponse joinedMember = memberService.join("testProvider", "1234", "testUrl");
		MemberSignUpRequest signUpRequest = new MemberSignUpRequest("닉네임", "강남역");
		memberService.signUp(joinedMember.id(), signUpRequest);

		//when
		Long wrongId = 123234345456L;

		//then
		assertThatThrownBy(() -> memberService.leave(wrongId))
			.isInstanceOf(MemberNotFoundException.class)
			.hasMessage("Cannot find Member for memberId=%d.".formatted(wrongId));
	}

	@Test
	@DisplayName("회원이 그룹에 호스트로 참여 중이면, 회원 탈퇴 시 그룹 또한 삭제된다.")
	void group_is_deleted_if_member_participates_as_host_when_member_leaves() {
		// given
		Member hostMember =
			memberRepository.save(Member.ofNickname("host"));

		GroupMember groupMemberAsGuest = GroupMember.newInstance(hostMember, ParticipantType.HOST);
		Group group = new Group(new GroupCapacity(GroupCapacity.MAX_CAPACITY), Group.GROUP_VALID_TIME);
		group.addGroupMember(groupMemberAsGuest);
		groupRepository.save(group);

		// when
		memberService.leave(hostMember.id());

		// then
		int actualAllMemberSize = memberRepository.findAll()
			.size();
		assertThat(actualAllMemberSize).isZero();

		int actualAllGroupSize = groupRepository.findAll()
			.size();
		assertThat(actualAllGroupSize).isZero();

		int actualAllGroupMemberSize = groupMemberRepository.findAll()
			.size();
		assertThat(actualAllGroupMemberSize).isZero();
	}

	@Test
	@DisplayName("회원이 그룹에 게스트로 참여 중이면, 회원 탈퇴 시 그룹은 삭제되지 않고 그룹멤버만 삭제된다.")
	void only_groupMember_is_deleted_if_member_participates_as_host_when_member_leaves() {
		// given
		Member guestMember =
			memberRepository.save(Member.ofNickname("guest"));

		GroupMember groupMemberAsGuest = GroupMember.newInstance(guestMember, ParticipantType.GUEST);
		Group group = new Group(new GroupCapacity(GroupCapacity.MAX_CAPACITY), Group.GROUP_VALID_TIME);
		group.addGroupMember(groupMemberAsGuest);
		groupRepository.save(group);

		// when
		memberService.leave(guestMember.id());

		// then
		int actualAllMemberSize = memberRepository.findAll()
			.size();
		assertThat(actualAllMemberSize).isZero();

		int actualAllGroupSize = groupRepository.findAll()
			.size();
		assertThat(actualAllGroupSize).isNotZero();

		int actualAllGroupMemberSize = groupMemberRepository.findAll()
			.size();
		assertThat(actualAllGroupMemberSize).isZero();
	}
}