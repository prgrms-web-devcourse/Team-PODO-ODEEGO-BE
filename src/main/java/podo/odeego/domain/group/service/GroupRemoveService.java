package podo.odeego.domain.group.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.group.entity.Group;
import podo.odeego.domain.group.repository.GroupRepository;

@Service
@Transactional
public class GroupRemoveService {

	private final GroupRepository groupRepository;
	private final GroupQueryService groupQueryService;

	public GroupRemoveService(GroupRepository groupRepository, GroupQueryService groupQueryService) {
		this.groupRepository = groupRepository;
		this.groupQueryService = groupQueryService;
	}

	public void remove(UUID groupId) {
		Group findGroup = groupQueryService.findById(groupId);
		groupRepository.delete(findGroup);
	}
}
