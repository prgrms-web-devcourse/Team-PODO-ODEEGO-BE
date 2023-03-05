package podo.odeego.domain.group.entity;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import podo.odeego.domain.group.exception.GroupHostAbsentException;
import podo.odeego.domain.group.exception.GroupHostStationAlreadyDefinedException;
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
		Member host = memberRepository.save(Member.ofNickname("host", "kakao", "12312123412"));
		Group savedGroup = groupRepository.save(new Group(new GroupCapacity(2L), LocalTime.of(1, 0)));
		savedGroup.addGroupMember(new GroupMember(savedGroup, host, ParticipantType.HOST));

		Station savedStation = stationRepository.save(new Station("가양역", null, 127.12314, 37.123124, "9"));

		// when
		savedGroup.defineHostStation(savedStation);

		// then
		GroupMember groupHost = groupMemberRepository.findGroupMembersByGroup(savedGroup)
			.stream()
			.filter(GroupMember::isHost)
			.findAny()
			.get();

		assertThat(groupHost.station().id())
			.isEqualTo(savedStation.id());
	}

	@DisplayName("그룹의 호스트가 없는 경우 역을 지정할 수 없다.")
	@Test
	void undefinedGroupHost() {
		// given
		Group savedGroup = groupRepository.save(new Group(new GroupCapacity(2L), LocalTime.of(1, 0)));
		Station station = stationRepository.save(new Station("가양역", null, 127.12314, 37.123124, "9"));

		// when && then
		assertThatThrownBy(() -> savedGroup.defineHostStation(station))
			.isInstanceOf(GroupHostAbsentException.class);
	}

	@DisplayName("그룹의 호스트는 역을 재지정할 수 없다.")
	@Test
	void alreadyDefinedStation() {
		// given
		Member host = memberRepository.save(Member.ofNickname("host", "kakao", "12312123412"));
		Group savedGroup = groupRepository.save(new Group(new GroupCapacity(2L), LocalTime.of(1, 0)));
		Station definedStation = stationRepository.save(new Station("가양역", null, 127.12314, 37.123124, "9"));
		savedGroup.addGroupMember(new GroupMember(savedGroup, host, definedStation, ParticipantType.HOST));

		Station redefinedStation = stationRepository.save(new Station("마두역", null, 127.12314, 37.123124, "3"));

		// when && then
		assertThatThrownBy(() -> savedGroup.defineHostStation(redefinedStation))
			.isInstanceOf(GroupHostStationAlreadyDefinedException.class);
	}
}
