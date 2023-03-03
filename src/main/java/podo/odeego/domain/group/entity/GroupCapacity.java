package podo.odeego.domain.group.entity;

import javax.persistence.Embeddable;

import podo.odeego.domain.group.exception.GroupCapacityOutOfBoundsException;

@Embeddable
public class GroupCapacity {

	private static final int MIN_CAPACITY = 2;
	private static final int MAX_CAPACITY = 4;

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
