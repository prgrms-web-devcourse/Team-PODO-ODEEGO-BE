package podo.odeego.domain.group.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalTime;
import java.util.List;

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
import podo.odeego.domain.group.exception.GroupAlreadyFullException;
import podo.odeego.domain.group.repository.GroupMemberRepository;
import podo.odeego.domain.group.repository.GroupRepository;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.repository.MemberRepository;
import podo.odeego.domain.midpoint.dto.StartSubmitRequest;
import podo.odeego.domain.station.entity.Station;
import podo.odeego.domain.station.repository.StationRepository;

@ExtendWith(SpringExtension.class)
@Import(TestConfig.class)
@DataJpaTest
class GroupMemberAddServiceTest {

	@Autowired
	private GroupMemberAddService groupMemberAddService;

	@Autowired
	private GroupMemberRepository groupMemberRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private StationRepository stationRepository;

	@DisplayName("회원은 그룹에 참여자로 등록할 수 있다.")
	@Test
	void add() {
		// given
		Member host = memberRepository.save(Member.ofNickname("host"));
		Group savedGroup = groupRepository.save(new Group(new GroupCapacity(2L), LocalTime.of(1, 0)));
		savedGroup.addGroupMember(GroupMember.newInstance(host, ParticipantType.HOST));

		Member guest = memberRepository.save(Member.ofNickname("test"));

		Station savedStation = stationRepository.save(new Station("가양역", 127.12314, 37.123124, "9"));

		StartSubmitRequest requestDto = new StartSubmitRequest(savedStation.name(), savedStation.latitude(),
			savedStation.longitude());

		// when
		groupMemberAddService.add(savedGroup.id(), guest.id(), requestDto);

		List<GroupMember> actualGroupMembers = groupMemberRepository.findGroupMembersByGroup(savedGroup);

		System.out.println("actualGroupMembers = " + actualGroupMembers);

		// then
		assertThat(actualGroupMembers.size())
			.isEqualTo(2);
	}

	@DisplayName("회원은 가득 찬 그룹에 들어갈 수 없다.")
	@Test
	void add_in_full_group() {
		// given
		Member host = memberRepository.save(Member.ofNickname("host"));
		Member guest = memberRepository.save(Member.ofNickname("guest"));
		Group savedGroup = groupRepository.save(new Group(new GroupCapacity(2L), LocalTime.of(1, 0)));
		savedGroup.addGroupMember(GroupMember.newInstance(host, ParticipantType.HOST));
		savedGroup.addGroupMember(GroupMember.newInstance(guest, ParticipantType.GUEST));

		Member savedMember = memberRepository.save(Member.ofNickname("test"));

		Station savedStation = stationRepository.save(new Station("가양역", 127.12314, 37.123124, "9"));

		StartSubmitRequest requestDto = new StartSubmitRequest(savedStation.name(), savedStation.latitude(),
			savedStation.longitude());

		// when && then
		assertThatThrownBy(() -> groupMemberAddService.add(savedGroup.id(), savedMember.id(), requestDto))
			.isInstanceOf(GroupAlreadyFullException.class);
	}
}