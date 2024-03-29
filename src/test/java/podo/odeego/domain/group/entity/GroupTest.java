package podo.odeego.domain.group.entity;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import podo.odeego.domain.group.exception.GroupHostAbsentException;
import podo.odeego.domain.group.exception.GroupMemberStationAlreadyDefinedException;
import podo.odeego.domain.group.repository.GroupMemberRepository;
import podo.odeego.domain.group.repository.GroupRepository;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.repository.MemberRepository;
import podo.odeego.domain.station.entity.Station;
import podo.odeego.domain.station.repository.StationRepository;

@DataJpaTest
class GroupTest {

	@Autowired
	private GroupMemberRepository groupMemberRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private StationRepository stationRepository;

	@DisplayName("그룹의 호스트는 역을 정할 수 있다.")
	@Test
	void defineHostStation() {
		// given
		Member host = memberRepository.save(Member.ofNickname("host"));
		Group group = new Group(new GroupCapacity(2L), LocalTime.of(1, 0));
		GroupMember groupHost = GroupMember.newInstance(host, ParticipantType.HOST);
		group.addGroupMember(groupHost);
		groupRepository.save(group);

		Station savedStation = stationRepository.save(new Station("가양역", 127.12314, 37.123124, "9"));

		// when
		group.defineHostStation(savedStation);

		// then
		GroupMember actualGroupHost = groupMemberRepository.findGroupMembersByGroup(group)
			.stream()
			.filter(GroupMember::isHost)
			.findAny()
			.get();

		assertThat(actualGroupHost.station().id())
			.isEqualTo(savedStation.id());
	}

	@DisplayName("그룹의 호스트가 없는 경우 역을 지정할 수 없다.")
	@Test
	void undefinedGroupHost() {
		// given
		Group savedGroup = groupRepository.save(new Group(new GroupCapacity(2L), LocalTime.of(1, 0)));
		Station station = stationRepository.save(new Station("가양역", 127.12314, 37.123124, "9"));

		// when && then
		assertThatThrownBy(() -> savedGroup.defineHostStation(station))
			.isInstanceOf(GroupHostAbsentException.class);
	}

	@DisplayName("그룹의 호스트는 역을 재지정할 수 없다.")
	@Test
	void alreadyDefinedStation() {
		// given
		Member host = memberRepository.save(Member.ofNickname("host"));
		Group group = new Group(new GroupCapacity(2L), LocalTime.of(1, 0));

		Station definedStation = stationRepository.save(new Station("가양역", 127.12314, 37.123124, "9"));
		GroupMember groupHost = GroupMember.newInstance(host, definedStation, ParticipantType.HOST);
		group.addGroupMember(groupHost);
		Group savedGroup = groupRepository.save(group);

		Station redefinedStation = stationRepository.save(new Station("마두역", 127.12314, 37.123124, "3"));

		// when && then
		assertThatThrownBy(() -> savedGroup.defineHostStation(redefinedStation))
			.isInstanceOf(GroupMemberStationAlreadyDefinedException.class);
	}
}
