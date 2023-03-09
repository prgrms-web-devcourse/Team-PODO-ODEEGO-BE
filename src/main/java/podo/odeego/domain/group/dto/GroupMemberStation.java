package podo.odeego.domain.group.dto;

import podo.odeego.domain.group.entity.Group;
import podo.odeego.domain.group.entity.GroupMember;
import podo.odeego.domain.group.entity.ParticipantType;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.station.entity.Station;

public record GroupMemberStation(
	Group group,
	Member member,
	Station station
) {
	public GroupMember toGuestEntity() {
		return GroupMember.newInstance(member, station, ParticipantType.GUEST);
	}
}
