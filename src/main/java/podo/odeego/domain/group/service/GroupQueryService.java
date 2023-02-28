package podo.odeego.domain.group.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.group.dto.Participants;
import podo.odeego.domain.group.dto.response.GroupResponse;
import podo.odeego.domain.group.entity.Group;
import podo.odeego.domain.group.entity.GroupMember;
import podo.odeego.domain.group.exception.GroupNotFoundException;
import podo.odeego.domain.group.repository.GroupMemberRepository;
import podo.odeego.domain.group.repository.GroupRepository;

@Service
@Transactional(readOnly = true)
public class GroupQueryService {

	private final GroupRepository groupRepository;
	private final GroupMemberRepository groupMemberRepository;

	public GroupQueryService(GroupRepository groupRepository, GroupMemberRepository groupMemberRepository) {
		this.groupRepository = groupRepository;
		this.groupMemberRepository = groupMemberRepository;
	}

	public GroupResponse getOne(UUID groupId) {
		Group findGroup = findById(groupId);
		List<GroupMember> findGroupMembers = groupMemberRepository.findGroupMembersByGroup(findGroup);
		return GroupResponse.from(findGroup, Participants.from(findGroupMembers));
	}

	public Group findById(UUID groupId) {
		return groupRepository.findById(groupId)
			.orElseThrow(() -> new GroupNotFoundException(
				"Cannot find group for groupId=%s".formatted(groupId.toString()))
			);
	}

	public void verifyGroupExists(UUID groupId) {
		if (!groupRepository.existsById(groupId)) {
			throw new GroupNotFoundException("Cannot find group for groupId=%s".formatted(groupId.toString()));
		}
	}
}
