package podo.odeego.domain.group.entity;

import static javax.persistence.CascadeType.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import podo.odeego.domain.group.exception.GroupAlreadyContainsException;
import podo.odeego.domain.group.exception.GroupAlreadyFullException;
import podo.odeego.domain.group.exception.GroupHostAbsentException;
import podo.odeego.domain.group.exception.GroupHostStationAlreadyDefinedException;
import podo.odeego.domain.station.entity.Station;
import podo.odeego.domain.type.BaseTime;
import podo.odeego.domain.util.TimeUtils;

@Entity
@Table(name = "`group`")
public class Group extends BaseTime {

	private static final Logger log = LoggerFactory.getLogger(Group.class);

	public static final LocalTime GROUP_VALID_TIME = LocalTime.of(1, 0);

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "group_id", columnDefinition = "BINARY(16)")
	private UUID id;

	@Embedded
	@Column(nullable = false)
	private GroupCapacity capacity;

	@Column(nullable = false)
	private LocalTime validTime;

	@OneToMany(mappedBy = "group", cascade = {REMOVE, PERSIST, MERGE})
	private List<GroupMember> groupMembers = new ArrayList<>();

	protected Group() {
	}

	public Group(GroupCapacity capacity, LocalTime validTime) {
		this.capacity = capacity;
		this.validTime = validTime;
	}

	public void addGroupMember(GroupMember groupMember) {
		if (capacity.isLessOrEqual(groupMembers.size())) {
			throw new GroupAlreadyFullException("Can't not add group member. Group is Full.");
		}

		if (isContains(groupMember)) {
			throw new GroupAlreadyContainsException(
				"Can't not add group member. Member is already contained. [member id]: %d".formatted(
					groupMember.member().id()));
		}

		this.groupMembers.add(groupMember);
	}

	public void defineHostStation(Station station) {
		GroupMember groupMember = groupMembers.stream()
			.filter(GroupMember::isHost)
			.findAny()
			.orElseThrow(() -> new GroupHostAbsentException("Can't find group host."));

		if (groupMember.hasStation()) {
			throw new GroupHostStationAlreadyDefinedException(
				"Host already has station. [saved station]: %s".formatted(station.name()));
		}

		groupMember.defineStation(station);
	}

	private boolean isContains(GroupMember groupMember) {
		return groupMembers.stream()
			.anyMatch(savedMember -> savedMember.getMemberId().equals(groupMember.getMemberId()));
	}

	public LocalTime getRemainingTime() {
		LocalDateTime currentTime = TimeUtils.getCurrentSeoulTime();

		if (isExpired(currentTime)) {
			return LocalTime.of(0, 0, 0);
		}

		Duration remainingTime = Duration.between(currentTime, getExpireTime());
		return TimeUtils.toLocalTime(remainingTime);
	}

	private boolean isExpired(LocalDateTime currentTime) {
		return getExpireTime().isBefore(currentTime);
	}

	private LocalDateTime getExpireTime() {
		log.info("Group.getExpireTime(): createdAt={}", super.createdAt());

		LocalDateTime expireDateTime = super.createdAt().plus(TimeUtils.toDuration(this.validTime));
		log.info("Group.getExpireTime(): expireDateTime={}", expireDateTime);

		return expireDateTime;
	}

	public UUID id() {
		return id;
	}

	public Long capacity() {
		return this.capacity.capacity();
	}

	public LocalTime validTime() {
		return validTime;
	}

	public List<GroupMember> groupMembers() {
		return Collections.unmodifiableList(this.groupMembers);
	}
}
