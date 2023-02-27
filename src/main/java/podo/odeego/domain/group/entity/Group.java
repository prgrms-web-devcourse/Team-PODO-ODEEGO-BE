package podo.odeego.domain.group.entity;

import static javax.persistence.FetchType.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.type.BaseTime;
import podo.odeego.domain.util.TimeUtils;

@Entity
@Table(name = "groups")
public class Group extends BaseTime {

	public static final LocalTime GROUP_VALID_TIME = LocalTime.of(1, 0);

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "group_id", columnDefinition = "BINARY(16)")
	private UUID id;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "owner_id", nullable = false)
	private Member owner;

	@Embedded
	@Column(nullable = false)
	private GroupCapacity capacity;

	@Column(nullable = false)
	private LocalTime validTime;

	protected Group() {
	}

	public Group(Member owner, GroupCapacity capacity, LocalTime validTime) {
		this.owner = owner;
		owner.participateGroup(this);
		this.capacity = capacity;
		this.validTime = validTime;
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
		return super.createdAt().plus(TimeUtils.toDuration(this.validTime));
	}

	public UUID id() {
		return id;
	}

	public Member owner() {
		return owner;
	}

	public GroupCapacity capacity() {
		return capacity;
	}

	public LocalTime validTime() {
		return validTime;
	}
}
