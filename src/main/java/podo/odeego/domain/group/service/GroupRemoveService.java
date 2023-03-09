package podo.odeego.domain.group.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.group.entity.Group;
import podo.odeego.domain.group.repository.GroupRepository;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.service.MemberFindService;

@Service
@Transactional
public class GroupRemoveService {

	private final MemberFindService memberFindService;
	private final GroupRepository groupRepository;
	private final GroupQueryService groupQueryService;

	public GroupRemoveService(
		MemberFindService memberFindService,
		GroupRepository groupRepository,
		GroupQueryService groupQueryService
	) {
		this.memberFindService = memberFindService;
		this.groupRepository = groupRepository;
		this.groupQueryService = groupQueryService;
	}

	public void remove(Long memberId, UUID groupId) {
		Member findMember = memberFindService.findById(memberId);
		Group findGroup = groupQueryService.findById(groupId);

		findGroup.verifyHostMatches(findMember);

		groupRepository.delete(findGroup);
	}
}
