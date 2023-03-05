package podo.odeego.domain.group.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.group.dto.GroupMemberStation;
import podo.odeego.domain.midpoint.dto.StartSubmitRequest;

@Service
@Transactional
public class GroupMemberAddService {

	private final GroupMemberValidateService validateService;

	public GroupMemberAddService(GroupMemberValidateService validateService) {
		this.validateService = validateService;
	}

	public void add(UUID groupId, Long memberId, StartSubmitRequest startSubmitRequest) {

		GroupMemberStation groupMemberStation = validateService.validateAndGet(groupId, memberId,
			startSubmitRequest.stationName());

		groupMemberStation.group()
			.addGroupMember(groupMemberStation.toGuestEntity());
	}
}
