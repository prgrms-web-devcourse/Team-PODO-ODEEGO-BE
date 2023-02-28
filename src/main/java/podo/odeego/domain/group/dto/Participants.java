package podo.odeego.domain.group.dto;

import java.util.Collections;
import java.util.List;

import podo.odeego.domain.group.entity.GroupMember;
import podo.odeego.domain.group.exception.GroupHostAbsentException;

public class Participants {

	private Long hostId;
	private List<Participant> participants;

	private Participants() {
	}

	public static Participants from(List<GroupMember> groupMembers) {
		Participants participants = new Participants();

		participants.hostId = findHostId(groupMembers);
		participants.participants = Participant.from(groupMembers);

		return participants;
	}

	private static Long findHostId(List<GroupMember> groupMembers) {
		return groupMembers.stream()
			.filter(GroupMember::isHost)
			.map(GroupMember::getMemberId)
			.findAny()
			.orElseThrow(() -> new GroupHostAbsentException(
				"Group host is absent."
			));
	}

	public Long hostId() {
		return hostId;
	}

	public List<Participant> participants() {
		return Collections.unmodifiableList(this.participants);
	}
}
