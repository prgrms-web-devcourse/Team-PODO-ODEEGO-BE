package podo.odeego.domain.group.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.group.dto.request.GroupCreateRequest;
import podo.odeego.domain.group.entity.Group;
import podo.odeego.domain.group.entity.GroupCapacity;
import podo.odeego.domain.group.entity.GroupMember;
import podo.odeego.domain.group.entity.ParticipantType;
import podo.odeego.domain.group.repository.GroupMemberRepository;
import podo.odeego.domain.group.repository.GroupRepository;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.service.MemberFindService;

@Service
@Transactional
public class GroupCreateService {

	private final GroupRepository groupRepository;
	private final GroupMemberRepository groupMemberRepository;

	private final MemberFindService memberFindService;

	public GroupCreateService(
		GroupRepository groupRepository,
		GroupMemberRepository groupMemberRepository,
		MemberFindService memberFindService
	) {
		this.groupRepository = groupRepository;
		this.groupMemberRepository = groupMemberRepository;
		this.memberFindService = memberFindService;
	}

	public UUID create(Long memberId, GroupCreateRequest createRequest) {
		Member findMember = memberFindService.findById(memberId);

		findMember.verifyNonOfGroupParticipating();

		Group group = new Group(new GroupCapacity(createRequest.capacity()), Group.GROUP_VALID_TIME);
		group.addGroupMember(GroupMember.newInstance(findMember, ParticipantType.HOST));

		groupRepository.save(group);

		return group.id();
	}
}
