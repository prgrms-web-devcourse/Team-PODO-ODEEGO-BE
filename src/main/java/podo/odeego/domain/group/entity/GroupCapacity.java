package podo.odeego.domain.group.entity;

import javax.persistence.Embeddable;

import podo.odeego.domain.group.exception.GroupCapacityOutOfBoundsException;

@Embeddable
public class GroupCapacity {

	public static final Long MIN_CAPACITY = 2L;
	public static final Long MAX_CAPACITY = 4L;

	private Long capacity;

	protected GroupCapacity() {
	}

	public GroupCapacity(Long capacity) {
		verifyCapacity(capacity);
		this.capacity = capacity;
	}

	private static boolean isValidCapacity(Long capacity) {
		return capacity >= MIN_CAPACITY && capacity <= MAX_CAPACITY;
	}

	private void verifyCapacity(Long capacity) {
		if (!isValidCapacity(capacity)) {
			throw new GroupCapacityOutOfBoundsException(
				"Capacity is not valid. It should be between '%d' and '%d'. But input was '%d'.".formatted(
					MIN_CAPACITY, MAX_CAPACITY, capacity
				)
			);
		}
	}

	public Long capacity() {
		return capacity;
	}

	public boolean isLessOrEqual(int size) {
		return capacity <= size;
	}
}
