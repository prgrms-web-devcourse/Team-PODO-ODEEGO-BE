package podo.odeego.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import podo.odeego.domain.group.repository.GroupMemberRepository;
import podo.odeego.domain.group.repository.GroupRepository;
import podo.odeego.domain.group.service.GroupMemberAddService;
import podo.odeego.domain.group.service.GroupMemberModifyService;
import podo.odeego.domain.group.service.GroupMemberValidateService;
import podo.odeego.domain.group.service.GroupQueryService;
import podo.odeego.domain.member.repository.MemberRepository;
import podo.odeego.domain.member.service.MemberFindService;
import podo.odeego.domain.member.service.MemberService;
import podo.odeego.domain.station.repository.StationRepository;
import podo.odeego.domain.station.service.StationFindService;

@TestConfiguration
public class TestConfig {

	@Bean
	public StationFindService stationFindService(
		StationRepository stationRepository
	) {
		return new StationFindService(stationRepository);
	}

	@Bean
	public MemberFindService memberFindService(
		MemberRepository memberRepository,
		StationFindService stationFindService
	) {
		return new MemberFindService(memberRepository, stationFindService);
	}

	@Bean
	public MemberService memberService(
		MemberRepository memberRepository
	) {
		return new MemberService(memberRepository);
	}

	@Bean
	public GroupQueryService groupQueryService(
		GroupRepository groupRepository,
		GroupMemberRepository groupMemberRepository,
		MemberFindService memberFindService
	) {
		return new GroupQueryService(groupRepository, groupMemberRepository, memberFindService);
	}

	@Bean
	public GroupMemberValidateService groupMemberValidateService(
		MemberFindService memberFindService,
		GroupQueryService groupQueryService,
		StationFindService stationFindService
	) {
		return new GroupMemberValidateService(
			memberFindService,
			groupQueryService,
			stationFindService
		);
	}

	@Bean
	public GroupMemberAddService groupMemberAddService(
		GroupMemberValidateService groupMemberValidateService
	) {
		return new GroupMemberAddService(groupMemberValidateService);
	}

	@Bean
	public GroupMemberModifyService groupMemberModifyService(
		GroupMemberValidateService groupMemberValidateService
	) {
		return new GroupMemberModifyService(groupMemberValidateService);
	}
}
