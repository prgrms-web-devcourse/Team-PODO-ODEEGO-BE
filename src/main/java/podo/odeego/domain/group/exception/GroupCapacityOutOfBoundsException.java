package podo.odeego.domain.group.exception;

import podo.odeego.web.error.ErrorCode;
import podo.odeego.web.error.exception.InvalidValueException;

public class GroupCapacityOutOfBoundsException extends InvalidValueException {

	public GroupCapacityOutOfBoundsException(String message) {
		super(message, ErrorCode.GROUP_CAPACITY_OUT_OF_BOUNDS);
	}
}
