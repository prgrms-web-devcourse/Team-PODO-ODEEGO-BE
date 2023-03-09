package podo.odeego.domain.group.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import podo.odeego.config.TestConfig;
import podo.odeego.domain.group.dto.HostStationModifyRequest;
import podo.odeego.domain.group.entity.Group;
import podo.odeego.domain.group.entity.GroupCapacity;
import podo.odeego.domain.group.entity.GroupMember;
import podo.odeego.domain.group.entity.ParticipantType;
import podo.odeego.domain.group.repository.GroupMemberRepository;
import podo.odeego.domain.group.repository.GroupRepository;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.repository.MemberRepository;
import podo.odeego.domain.station.entity.Station;
import podo.odeego.domain.station.repository.StationRepository;

@ExtendWith(SpringExtension.class)
@Import(TestConfig.class)
@DataJpaTest
class GroupMemberModifyServiceTest {

	@Autowired
	private GroupMemberModifyService modifyService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private GroupMemberRepository groupMemberRepository;

	@DisplayName("그룹 호스트는 역을 지정할 수 있다.")
	@Test
	void define() {
		// given
		Member host = memberRepository.save(Member.ofNickname("host", "kakao", "12312123412"));
		Group group = new Group(new GroupCapacity(2L), LocalTime.of(1, 0));
		GroupMember groupHost = GroupMember.newInstance(host, ParticipantType.HOST);
		group.addGroupMember(groupHost);
		Group savedGroup = groupRepository.save(group);

		Station savedStation = stationRepository.save(new Station("가양역", 127.12314, 37.123124, "9"));

		HostStationModifyRequest request = new HostStationModifyRequest(savedStation.name(), savedStation.latitude(),
			savedStation.longitude());

		// when
		modifyService.define(savedGroup.id(), host.id(), request);

		// then
		GroupMember actualGroupHost = groupMemberRepository.findGroupMembersByGroup(savedGroup)
			.stream()
			.filter(GroupMember::isHost)
			.findAny()
			.get();

		assertThat(actualGroupHost.station().id())
			.isEqualTo(savedStation.id());
	}
}