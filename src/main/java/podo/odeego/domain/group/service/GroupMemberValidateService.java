package podo.odeego.domain.group.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.group.dto.GroupMemberStation;
import podo.odeego.domain.group.entity.Group;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.service.MemberFindService;
import podo.odeego.domain.station.entity.Station;
import podo.odeego.domain.station.service.StationFindService;

@Service
@Transactional(readOnly = true)
public class GroupMemberValidateService {

	private final MemberFindService memberFindService;
	private final GroupQueryService groupQueryService;
	private final StationFindService stationFindService;

	public GroupMemberValidateService(MemberFindService memberFindService, GroupQueryService groupQueryService,
		StationFindService stationFindService) {
		this.memberFindService = memberFindService;
		this.groupQueryService = groupQueryService;
		this.stationFindService = stationFindService;
	}

	public GroupMemberStation validateAndGet(UUID groupId, Long memberId, String stationName) {
		Member authorizedMember = memberFindService.findById(memberId);

		Group savedGroup = groupQueryService.findById(groupId);

		Station memberStation = stationFindService.findByName(stationName);

		return new GroupMemberStation(savedGroup, authorizedMember, memberStation);
	}
}
