package podo.odeego.domain.group.entity;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;

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

	public GroupMember(Group group, Member member, ParticipantType type) {
		this(group, member, null, type);
	}

	public GroupMember(Group group, Member member, Station station, ParticipantType type) {
		this.group = group;
		this.member = member;
		member.addGroupMember(this);
		this.station = station;
		this.type = type;
	}

	public boolean isHost() {
		return this.type.isHost();
	}

	public Long getMemberId() {
		return this.member.id();
	}

	public String getNickname() {
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
