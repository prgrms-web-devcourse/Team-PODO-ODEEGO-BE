package podo.odeego.domain.group.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import podo.odeego.domain.group.entity.Group;
import podo.odeego.domain.group.entity.GroupCapacity;
import podo.odeego.domain.group.entity.GroupMember;
import podo.odeego.domain.group.entity.ParticipantType;
import podo.odeego.domain.group.exception.GroupAlreadyContainsException;
import podo.odeego.domain.group.exception.GroupAlreadyFullException;
import podo.odeego.domain.group.repository.GroupMemberRepository;
import podo.odeego.domain.group.repository.GroupRepository;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.repository.MemberRepository;
import podo.odeego.domain.member.service.MemberFindService;
import podo.odeego.domain.midpoint.dto.StartSubmitRequest;
import podo.odeego.domain.station.entity.Station;
import podo.odeego.domain.station.repository.StationRepository;
import podo.odeego.domain.station.service.StationFindService;

@DataJpaTest
class GroupMemberAddServiceTest {

	private static final Logger log = LoggerFactory.getLogger(GroupMemberAddServiceTest.class);

	@Autowired
	private MemberFindService memberFindService;

	@Autowired
	private GroupQueryService groupQueryService;

	@Autowired
	private StationFindService stationFindService;

	@Autowired
	private GroupMemberAddService groupMemberAddService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private GroupMemberRepository groupMemberRepository;

	@TestConfiguration
	static class TestConfig {

		@Bean
		public MemberFindService memberFindService(
			MemberRepository memberRepository
		) {
			return new MemberFindService(memberRepository);
		}

		@Bean
		public GroupQueryService groupQueryService(
			GroupRepository groupRepository,
			GroupMemberRepository groupMemberRepository
		) {
			return new GroupQueryService(groupRepository, groupMemberRepository);
		}

		@Bean
		public StationFindService stationFindService(
			StationRepository stationRepository
		) {
			return new StationFindService(stationRepository);
		}

		@Bean
		public GroupMemberAddService groupMemberAddService(
			MemberFindService memberFindService,
			GroupQueryService groupQueryService,
			StationFindService stationFindService
		) {
			return new GroupMemberAddService(
				memberFindService,
				groupQueryService,
				stationFindService
			);
		}

	}

	@DisplayName("회원은 그룹에 참여자로 등록할 수 있다.")
	@Test
	void add() {
		// given
		Member host = memberRepository.save(new Member("host", "kakao", "12312123412"));
		Group savedGroup = groupRepository.save(new Group(new GroupCapacity(2L), LocalTime.of(1, 0)));
		savedGroup.addGroupMember(new GroupMember(savedGroup, host, ParticipantType.HOST));

		Member savedMember = memberRepository.save(new Member("test", "kakao", "12312412"));

		Station savedStation = stationRepository.save(new Station("가양역", null, 127.12314, 37.123124, "9"));

		StartSubmitRequest requestDto = new StartSubmitRequest(savedGroup.id(), savedStation.name(),
			savedStation.latitude(), savedStation.longitude());

		// when
		groupMemberAddService.add(savedMember.id(), requestDto);

		List<GroupMember> actualGroupMembers = savedGroup.groupMembers();

		// then
		assertThat(actualGroupMembers.size())
			.isEqualTo(2);
	}

	@DisplayName("회원은 가득 찬 그룹에 들어갈 수 없다.")
	@Test
	void add_in_full_group() {
		// given
		Member host = memberRepository.save(new Member("host", "kakao", "12312123412"));
		Member guest = memberRepository.save(new Member("guest", "kakao", "12312123412"));
		Group savedGroup = groupRepository.save(new Group(new GroupCapacity(2L), LocalTime.of(1, 0)));
		savedGroup.addGroupMember(new GroupMember(savedGroup, host, ParticipantType.HOST));
		savedGroup.addGroupMember(new GroupMember(savedGroup, guest, ParticipantType.GUEST));

		Member savedMember = memberRepository.save(new Member("test", "kakao", "12312412"));

		Station savedStation = stationRepository.save(new Station("가양역", null, 127.12314, 37.123124, "9"));

		StartSubmitRequest requestDto = new StartSubmitRequest(savedGroup.id(), savedStation.name(),
			savedStation.latitude(), savedStation.longitude());

		// when && then
		assertThatThrownBy(() -> groupMemberAddService.add(savedMember.id(), requestDto))
			.isInstanceOf(GroupAlreadyFullException.class);
	}

	@DisplayName("회원은 이미 참가한 그룹에 들어갈 수 없다.")
	@Test
	void add_in_participated_group() {
		// given
		Station savedStation = stationRepository.save(new Station("가양역", null, 127.12314, 37.123124, "9"));

		Member host = memberRepository.save(new Member("host", "kakao", "12312123412"));
		Group savedGroup = groupRepository.save(new Group(new GroupCapacity(3L), LocalTime.of(1, 0)));
		savedGroup.addGroupMember(new GroupMember(savedGroup, host, ParticipantType.HOST));

		Member savedMember = memberRepository.save(new Member("test", "kakao", "12312412"));
		savedGroup.addGroupMember(new GroupMember(savedGroup, savedMember, savedStation, ParticipantType.GUEST));

		StartSubmitRequest requestDto = new StartSubmitRequest(savedGroup.id(), savedStation.name(),
			savedStation.latitude(), savedStation.longitude());

		// when && then
		assertThatThrownBy(() -> groupMemberAddService.add(savedMember.id(), requestDto))
			.isInstanceOf(GroupAlreadyContainsException.class);
	}
}