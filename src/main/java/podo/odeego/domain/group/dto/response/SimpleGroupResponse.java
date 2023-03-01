package podo.odeego.domain.group.dto.response;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import podo.odeego.domain.group.entity.Group;

public class SimpleGroupResponse {

	private UUID groupId;
	private Long capacity;
	private LocalDateTime createdAt;
	private LocalTime remainingTime;

	private SimpleGroupResponse() {
	}

	public static SimpleGroupResponse from(Group group) {
		SimpleGroupResponse response = new SimpleGroupResponse();

		response.groupId = group.id();
		response.capacity = group.capacity();
		response.createdAt = group.createdAt();
		response.remainingTime = group.getRemainingTime();

		return response;
	}

	public static List<SimpleGroupResponse> from(List<Group> groups) {
		return groups.stream()
			.map(SimpleGroupResponse::from)
			.toList();
	}

	public UUID getGroupId() {
		return groupId;
	}

	public Long getCapacity() {
		return capacity;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalTime getRemainingTime() {
		return remainingTime;
	}
}
