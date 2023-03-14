package podo.odeego.domain.group.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.group.dto.GroupMemberExistResponse;
import podo.odeego.domain.group.dto.Participants;
import podo.odeego.domain.group.dto.response.GroupResponse;
import podo.odeego.domain.group.dto.response.GroupResponses;
import podo.odeego.domain.group.entity.Group;
import podo.odeego.domain.group.entity.GroupMember;
import podo.odeego.domain.group.exception.GroupNotFoundException;
import podo.odeego.domain.group.repository.GroupMemberRepository;
import podo.odeego.domain.group.repository.GroupRepository;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.service.MemberFindService;

@Service
@Transactional(readOnly = true)
public class GroupQueryService {

	private final GroupRepository groupRepository;
	private final GroupMemberRepository groupMemberRepository;
	private final MemberFindService memberFindService;

	public GroupQueryService(
		GroupRepository groupRepository,
		GroupMemberRepository groupMemberRepository,
		MemberFindService memberFindService
	) {
		this.groupRepository = groupRepository;
		this.groupMemberRepository = groupMemberRepository;
		this.memberFindService = memberFindService;
	}

	public GroupResponse getOne(Long memberId, UUID groupId) {
		Member findMember = memberFindService.findById(memberId);
		Group findGroup = findById(groupId);

		findGroup.verifyHostMatches(findMember);

		List<GroupMember> findGroupMembers = groupMemberRepository.findGroupMembersByGroup(findGroup);
		return GroupResponse.from(findGroup, Participants.from(findGroupMembers));
	}

	public GroupResponses getAll(Long memberId) {
		Member findMember = memberFindService.findById(memberId);

		List<Group> groups = findMember.getGroupMembers()
			.stream()
			.map(GroupMember::getGroupId)
			.map(this::findById)
			.toList();

		return GroupResponses.from(groups);
	}

	public Group findById(UUID groupId) {
		return groupRepository.findById(groupId)
			.orElseThrow(
				() -> new GroupNotFoundException("Cannot find group for groupId=%s".formatted(groupId.toString())));
	}

	public void verifyGroupExists(UUID groupId) {
		if (!groupRepository.existsById(groupId)) {
			throw new GroupNotFoundException("Cannot find group for groupId=%s".formatted(groupId.toString()));
		}
	}

	public GroupMemberExistResponse isSubmitted(UUID groupId, Long memberId) {
		Group group = groupRepository.findFetchById(groupId)
			.orElseThrow(
				() -> new GroupNotFoundException("Cannot find group for groupId=%s".formatted(groupId.toString())));

		return new GroupMemberExistResponse(group.isGroupMemberSubmitted(memberId));
	}
}
