package podo.odeego.domain.group.dto;

import java.util.List;

import podo.odeego.domain.group.entity.GroupMember;
import podo.odeego.domain.station.dto.StationGps;

public class Participant {

	private Long memberId;
	private String nickname;
	private StationGps start;

	private Participant() {
	}

	private static Participant from(GroupMember groupMember) {
		Participant participant = new Participant();

		participant.memberId = groupMember.getMemberId();
		participant.nickname = groupMember.getMemberNickname();
		participant.start = StationGps.from(groupMember.station());

		return participant;
	}

	public static List<Participant> from(List<GroupMember> groupMembers) {
		return groupMembers.stream()
			.map(Participant::from)
			.toList();
	}

	public Long getMemberId() {
		return memberId;
	}

	public String getNickname() {
		return nickname;
	}

	public StationGps getStart() {
		return start;
	}
}
