package podo.odeego.domain.group.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.group.entity.Group;
import podo.odeego.domain.group.entity.GroupMember;
import podo.odeego.domain.group.entity.ParticipantType;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.service.MemberFindService;
import podo.odeego.domain.midpoint.dto.StartSubmitRequest;
import podo.odeego.domain.station.entity.Station;
import podo.odeego.domain.station.service.StationFindService;

@Service
@Transactional
public class GroupMemberAddService {

	private final MemberFindService memberFindService;
	private final GroupQueryService groupQueryService;
	private final StationFindService stationFindService;

	public GroupMemberAddService(MemberFindService memberFindService, GroupQueryService groupQueryService,
		StationFindService stationFindService) {
		this.memberFindService = memberFindService;
		this.groupQueryService = groupQueryService;
		this.stationFindService = stationFindService;
	}

	public void add(Long memberId, StartSubmitRequest startSubmitRequest) {

		Member authorizedMember = validateMemberAndGet(memberId);

		Group savedGroup = validateGroupAndGet(startSubmitRequest.groupId());

		Station memberStation = validateStationAndGet(startSubmitRequest.stationName());

		savedGroup.addGroupMember(new GroupMember(
			savedGroup,
			authorizedMember,
			memberStation,
			ParticipantType.GUEST
		));
	}

	private Station validateStationAndGet(String stationName) {
		return stationFindService.findByName(stationName);
	}

	private Group validateGroupAndGet(UUID groupId) {
		return groupQueryService.findById(groupId);
	}

	private Member validateMemberAndGet(Long memberId) {
		return memberFindService.findById(memberId);
	}
}
