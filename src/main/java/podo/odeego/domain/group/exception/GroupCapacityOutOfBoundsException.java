package podo.odeego.domain.group.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.InvalidValueException;

public class GroupCapacityOutOfBoundsException extends InvalidValueException {

	public GroupCapacityOutOfBoundsException(String message) {
		super(message, ErrorCode.GROUP_CAPACITY_OUT_OF_BOUNDS);
	}
}
