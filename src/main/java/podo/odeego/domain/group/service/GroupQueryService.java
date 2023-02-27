package podo.odeego.domain.group.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.group.entity.Group;
import podo.odeego.domain.group.exception.GroupNotFoundException;
import podo.odeego.domain.group.repository.GroupRepository;

@Service
@Transactional(readOnly = true)
public class GroupQueryService {

	private final GroupRepository groupRepository;

	public GroupQueryService(GroupRepository groupRepository) {
		this.groupRepository = groupRepository;
	}

	public Group findById(UUID groupId) {
		return groupRepository.findById(groupId)
			.orElseThrow(() -> new GroupNotFoundException(""));
	}
}
