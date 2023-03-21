package podo.odeego.domain.group.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.group.entity.Group;
import podo.odeego.domain.group.entity.GroupMember;
import podo.odeego.domain.group.repository.GroupMemberRepository;
import podo.odeego.domain.group.repository.GroupRepository;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.service.MemberFindService;

@Service
@Transactional
public class GroupRemoveService {

	private final GroupRepository groupRepository;
	private final GroupMemberRepository groupMemberRepository;
	private final GroupQueryService groupQueryService;
	private final MemberFindService memberFindService;

	public GroupRemoveService(
		GroupRepository groupRepository,
		GroupMemberRepository groupMemberRepository,
		GroupQueryService groupQueryService,
		MemberFindService memberFindService
	) {
		this.groupRepository = groupRepository;
		this.groupMemberRepository = groupMemberRepository;
		this.groupQueryService = groupQueryService;
		this.memberFindService = memberFindService;
	}

	public void remove(Long memberId, UUID groupId) {
		Member findMember = memberFindService.findById(memberId);
		Group findGroup = groupQueryService.findById(groupId);

		findGroup.verifyHostMatches(findMember);

		groupRepository.delete(findGroup);
	}

	public void deleteGroupInfoByParticipantType(Long memberId) {
		Optional<GroupMember> possibleGroupMember = groupMemberRepository.findByMemberId(memberId);
		possibleGroupMember.ifPresent(this::deleteGroupInfoByParticipantType);
	}

	private void deleteGroupInfoByParticipantType(GroupMember groupMember) {
		Group participatingGroup = groupMember.group();

		if (groupMember.isHost()) {
			groupRepository.delete(participatingGroup);
			return;
		}

		participatingGroup.removeGroupMember(groupMember);
		groupMemberRepository.delete(groupMember);
	}
}
