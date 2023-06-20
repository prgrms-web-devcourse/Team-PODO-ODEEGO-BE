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
import podo.odeego.domain.member.dto.MemberDefaultStationGetResponse;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.repository.MemberRepository;
import podo.odeego.domain.station.dto.StationInfo;
import podo.odeego.domain.station.entity.Station;
import podo.odeego.domain.station.repository.StationRepository;

@ExtendWith(SpringExtension.class)
@Import(TestConfig.class)
@DataJpaTest
class MemberFindServiceTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private MemberFindService memberFindService;

	@DisplayName("멤버의 기본 지하철 정보를 조회할 수 있다.")
	@Test
	void findDefaultStation() {
		// given
		Station savedStation = stationRepository.save(new Station("강남역", 123.123, 12.231, "9호선"));
		Member savedMember = memberRepository.save(
			Member.ofStationName("test", savedStation.name()));

		MemberDefaultStationGetResponse expect = new MemberDefaultStationGetResponse(new StationInfo(savedStation));

		// when
		MemberDefaultStationGetResponse actual = memberFindService.findDefaultStation(savedMember.id());

		// then
		Assertions.assertThat(actual)
			.isEqualTo(expect);
	}
}