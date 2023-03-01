package podo.odeego.domain.group.dto.response;

import java.util.ArrayList;
import java.util.List;

import podo.odeego.domain.group.entity.Group;

public class GroupResponses {

	private List<SimpleGroupResponse> groups = new ArrayList<>();

	private GroupResponses() {
	}

	public static GroupResponses from(List<Group> groups) {
		GroupResponses responses = new GroupResponses();
		responses.groups = SimpleGroupResponse.from(groups);
		return responses;
	}

	public List<SimpleGroupResponse> getGroups() {
		return groups;
	}
}
