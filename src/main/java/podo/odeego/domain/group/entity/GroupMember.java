package podo.odeego.domain.group.entity;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.station.entity.Station;
import podo.odeego.domain.type.BaseTime;

@Entity
public class GroupMember extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "group_member_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "group_id", nullable = false)
	private Group group;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "station_id")
	private Station station;

	@Enumerated(value = STRING)
	@Column(nullable = false)
	private ParticipantType type;

	protected GroupMember() {
	}

	private GroupMember(Member member, Station station, ParticipantType type) {
		this.member = member;
		this.station = station;
		this.type = type;
	}

	public static GroupMember newInstance(Member member, ParticipantType type) {
		return newInstance(member, null, type);
	}

	public static GroupMember newInstance(Member member, Station station, ParticipantType type) {
		GroupMember groupMember = new GroupMember(member, station, type);
		member.addGroupMember(groupMember);
		return groupMember;
	}

	public void assignGroup(Group group) {
		this.group = group;
	}

	public void defineStation(Station station) {
		this.station = Objects.requireNonNull(station);
	}

	public boolean isMemberIdMatches(Long memberId) {
		return Objects.equals(getMemberId(), memberId);
	}

	public boolean hasStation() {
		return station != null;
	}

	public boolean isHost() {
		return this.type.isHost();
	}

	public Long getMemberId() {
		return this.member.id();
	}

	public UUID getGroupId() {
		return this.group.id();
	}

	public String getMemberNickname() {
		return this.member.nickname();
	}

	public Long id() {
		return id;
	}

	public Group group() {
		return group;
	}

	public Member member() {
		return member;
	}

	public Station station() {
		return station;
	}

	public ParticipantType type() {
		return type;
	}
}
