package podo.odeego.domain.group.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.group.dto.request.GroupCreateRequest;
import podo.odeego.domain.group.entity.Group;
import podo.odeego.domain.group.entity.GroupCapacity;
import podo.odeego.domain.group.repository.GroupRepository;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.service.MemberQueryService;

@Service
@Transactional
public class GroupCreateService {

	private final GroupRepository groupRepository;
	private final MemberQueryService memberQueryService;

	public GroupCreateService(
		GroupRepository groupRepository,
		MemberQueryService memberQueryService
	) {
		this.groupRepository = groupRepository;
		this.memberQueryService = memberQueryService;
	}

	public UUID create(Long memberId, GroupCreateRequest createRequest) {
		Member findMember = memberQueryService.findById(memberId);

		findMember.verifyNonOfGroupParticipating();

		Group group = new Group(findMember, new GroupCapacity(createRequest.capacity()), Group.GROUP_VALID_TIME);
		groupRepository.save(group);

		return group.id();
	}
}
