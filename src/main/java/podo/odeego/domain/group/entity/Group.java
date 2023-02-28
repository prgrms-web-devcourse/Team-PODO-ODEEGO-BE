package podo.odeego.domain.group.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import podo.odeego.domain.type.BaseTime;
import podo.odeego.domain.util.TimeUtils;

@Entity
@Table(name = "`group`")
public class Group extends BaseTime {

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

	protected Group() {
	}

	public Group(GroupCapacity capacity, LocalTime validTime) {
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

	public Long capacity() {
		return this.capacity.capacity();
	}

	public LocalTime validTime() {
		return validTime;
	}
}
