package podo.odeego.domain.group.entity;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import podo.odeego.domain.group.repository.GroupMemberRepository;
import podo.odeego.domain.group.repository.GroupRepository;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.repository.MemberRepository;
import podo.odeego.domain.station.entity.Station;
import podo.odeego.domain.station.repository.StationRepository;

@DataJpaTest
class GroupMemberTest {

	@Autowired
	private GroupMemberRepository groupMemberRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private StationRepository stationRepository;

	@DisplayName("그룹 멤버는 역을 지정할 수 있다.")
	@Test
	void defineStation() {
		// given
		Member host = memberRepository.save(Member.ofNickname("host"));
		Group group = new Group(new GroupCapacity(2L), LocalTime.of(1, 0));
		GroupMember groupHost = GroupMember.newInstance(host, ParticipantType.HOST);
		group.addGroupMember(groupHost);
		groupRepository.save(group);

		Station savedStation = stationRepository.save(new Station("가양역", 127.12314, 37.123124, "9"));

		// when
		groupHost.defineStation(savedStation);

		// then
		GroupMember actual = groupMemberRepository.findById(groupHost.id())
			.get();

		assertThat(actual.station().id())
			.isEqualTo(savedStation.id());
	}

	@DisplayName("그룹 멤버는 null 값을 역으로 지정할 수 없다.")
	@Test
	void defineNullStation() {
		// given
		Member host = memberRepository.save(Member.ofNickname("host"));
		Group group = new Group(new GroupCapacity(2L), LocalTime.of(1, 0));
		GroupMember groupHost = GroupMember.newInstance(host, ParticipantType.HOST);
		group.addGroupMember(groupHost);

		groupRepository.save(group);

		// when && then
		assertThatThrownBy(() -> groupHost.defineStation(null))
			.isInstanceOf(NullPointerException.class);
	}
}
