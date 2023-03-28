package podo.odeego.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import podo.odeego.domain.group.repository.GroupMemberRepository;
import podo.odeego.domain.group.repository.GroupRepository;
import podo.odeego.domain.group.service.GroupMemberAddService;
import podo.odeego.domain.group.service.GroupMemberModifyService;
import podo.odeego.domain.group.service.GroupMemberValidateService;
import podo.odeego.domain.group.service.GroupQueryService;
import podo.odeego.domain.group.service.GroupRemoveService;
import podo.odeego.domain.member.repository.MemberRepository;
import podo.odeego.domain.member.service.MemberFindService;
import podo.odeego.domain.member.service.MemberService;
import podo.odeego.domain.place.repository.PlaceRepository;
import podo.odeego.domain.place.service.PlaceQueryServiceImpl;
import podo.odeego.domain.station.repository.StationRepository;
import podo.odeego.domain.station.service.StationFindService;

@TestConfiguration
public class TestConfig {

	// Station
	@Bean
	public StationFindService stationFindService(
		StationRepository stationRepository
	) {
		return new StationFindService(stationRepository);
	}

	// Member
	@Bean
	public MemberFindService memberFindService(
		MemberRepository memberRepository,
		StationFindService stationFindService
	) {
		return new MemberFindService(memberRepository, stationFindService);
	}

	@Bean
	public MemberService memberService(
		MemberRepository memberRepository,
		MemberFindService memberFindService,
		GroupRemoveService groupRemoveService
	) {
		return new MemberService(
			memberRepository,
			memberFindService,
			groupRemoveService
		);
	}

	// Group
	@Bean
	public GroupQueryService groupQueryService(
		GroupRepository groupRepository,
		GroupMemberRepository groupMemberRepository,
		MemberFindService memberFindService
	) {
		return new GroupQueryService(groupRepository, groupMemberRepository, memberFindService);
	}

	@Bean
	public GroupRemoveService groupRemoveService(
		GroupRepository groupRepository,
		GroupMemberRepository groupMemberRepository,
		GroupQueryService groupQueryService,
		MemberFindService memberFindService
	) {
		return new GroupRemoveService(groupRepository, groupMemberRepository, groupQueryService, memberFindService);
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

	// Place
	@Bean
	public PlaceQueryServiceImpl placeQueryServiceImpl(
		PlaceRepository placeRepository
	) {
		return new PlaceQueryServiceImpl(placeRepository);
	}
}
