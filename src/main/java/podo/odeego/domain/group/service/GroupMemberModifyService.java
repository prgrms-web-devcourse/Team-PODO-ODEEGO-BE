package podo.odeego.domain.group.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.group.dto.GroupMemberStation;
import podo.odeego.domain.group.dto.HostStationModifyRequest;

@Service
@Transactional
public class GroupMemberModifyService {

	private final GroupMemberValidateService validateService;

	public GroupMemberModifyService(GroupMemberValidateService validateService) {
		this.validateService = validateService;
	}

	public void define(UUID groupId, Long hostId, HostStationModifyRequest hostStationModifyRequest) {
		GroupMemberStation groupMemberStation = validateService.validateAndGet(groupId, hostId,
			hostStationModifyRequest.stationName());

		groupMemberStation.group()
			.defineHostStation(groupMemberStation.station());
	}
}
