package podo.odeego.domain.group.entity;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import podo.odeego.domain.group.exception.GroupAlreadyContainsException;
import podo.odeego.domain.group.exception.GroupAlreadyFullException;
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

	@OneToMany(mappedBy = "group")
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

		if (groupMembers.contains(groupMember)) {
			throw new GroupAlreadyContainsException(
				MessageFormat.format("Can't not add group member. Member is already contained. [group member]:{0}",
					groupMember.member()));
		}

		this.groupMembers.add(groupMember);
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

	public List<GroupMember> groupMembers() {
		return Collections.unmodifiableList(this.groupMembers);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Group group = (Group)o;
		return Objects.equals(id, group.id) && Objects.equals(capacity, group.capacity)
			&& Objects.equals(validTime, group.validTime) && Objects.equals(groupMembers,
			group.groupMembers);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, capacity, validTime, groupMembers);
	}
}
