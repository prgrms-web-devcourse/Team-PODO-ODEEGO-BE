package podo.odeego.domain.group.dto.response;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import podo.odeego.domain.group.dto.Participant;
import podo.odeego.domain.group.dto.Participants;
import podo.odeego.domain.group.entity.Group;

public class GroupResponse {

	private Long capacity;
	private LocalTime remainingTime;
	private Long hostId;
	private Participants participants;

	private GroupResponse() {
	}

	public static GroupResponse from(Group group, Participants participants) {
		GroupResponse response = new GroupResponse();

		response.capacity = group.capacity();
		response.remainingTime = group.getRemainingTime();
		response.hostId = participants.hostId();
		response.participants = participants;

		return response;
	}

	public Long getCapacity() {
		return capacity;
	}

	public Long getHostId() {
		return hostId;
	}

	public LocalTime getRemainingTime() {
		return remainingTime;
	}

	public List<Participant> getParticipants() {
		return Collections.unmodifiableList(this.participants.participants());
	}
}
