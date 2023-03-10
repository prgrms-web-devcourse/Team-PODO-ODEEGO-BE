package podo.odeego.domain.group.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import podo.odeego.config.TestConfig;
import podo.odeego.domain.group.dto.GroupMemberStation;
import podo.odeego.domain.group.entity.Group;
import podo.odeego.domain.group.entity.GroupCapacity;
import podo.odeego.domain.group.exception.GroupNotFoundException;
import podo.odeego.domain.group.repository.GroupRepository;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.exception.MemberNotFoundException;
import podo.odeego.domain.member.repository.MemberRepository;
import podo.odeego.domain.station.dto.StationInfo;
import podo.odeego.domain.station.entity.Station;
import podo.odeego.domain.station.exception.StationNotFoundException;
import podo.odeego.domain.station.repository.StationRepository;

@ExtendWith(SpringExtension.class)
@Import(TestConfig.class)
@DataJpaTest
class GroupMemberValidateServiceTest {

	@Autowired
	private GroupMemberValidateService validateService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private StationRepository stationRepository;

	@DisplayName("검증된 그룹멤버를 만들 수 있다.")
	@Test
	void validateAndGet() {
		// given
		Member host = memberRepository.save(Member.ofNickname("host", "kakao", "12312123412"));
		Group savedGroup = groupRepository.save(new Group(new GroupCapacity(2L), LocalTime.of(1, 0)));
		StationInfo savedStation = new StationInfo(
			stationRepository.save(new Station("가양역", 127.12314, 37.123124, "9")));

		GroupMemberStation expect = new GroupMemberStation(savedGroup, host, savedStation);

		// when
		GroupMemberStation actual = validateService.validateAndGet(savedGroup.id(), host.id(), savedStation.name());

		// then
		assertThat(actual.group().id())
			.isEqualTo(expect.group().id());
		assertThat(actual.member().id())
			.isEqualTo(expect.member().id());
		assertThat(actual.station().id())
			.isEqualTo(expect.station().id());
	}

	@DisplayName("식별되지 않는 멤버로 검증된 그룹멤버를 만들 수 없다.")
	@Test
	void undefinedMember() {
		// given
		Long undefinedMemberId = 1L;
		Station savedStation = stationRepository.save(new Station("가양역", 127.12314, 37.123124, "9"));
		Group savedGroup = groupRepository.save(new Group(new GroupCapacity(2L), LocalTime.of(1, 0)));

		// when && then
		assertThatThrownBy(
			() -> validateService.validateAndGet(savedGroup.id(), undefinedMemberId, savedStation.name()))
			.isInstanceOf(MemberNotFoundException.class);
	}

	@DisplayName("식별되지 않는 그룹으로 검증된 그룹멤버를 만들 수 없다.")
	@Test
	void undefinedGroup() {
		// given
		Member host = memberRepository.save(Member.ofNickname("host", "kakao", "12312123412"));
		Station savedStation = stationRepository.save(new Station("가양역", 127.12314, 37.123124, "9"));
		UUID undefinedGroupId = UUID.randomUUID();

		// when && then
		assertThatThrownBy(() -> validateService.validateAndGet(undefinedGroupId, host.id(), savedStation.name()))
			.isInstanceOf(GroupNotFoundException.class);
	}

	@DisplayName("식별되지 않는 역으로 검증된 그룹멤버를 만들 수 없다.")
	@Test
	void undefinedStation() {
		// given
		Member host = memberRepository.save(Member.ofNickname("host", "kakao", "12312123412"));
		String undefinedStationName = "모르는역";
		Group savedGroup = groupRepository.save(new Group(new GroupCapacity(2L), LocalTime.of(1, 0)));

		// when && then
		assertThatThrownBy(() -> validateService.validateAndGet(savedGroup.id(), host.id(), undefinedStationName))
			.isInstanceOf(StationNotFoundException.class);
	}
}