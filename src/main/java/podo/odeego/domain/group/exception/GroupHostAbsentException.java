package podo.odeego.domain.group.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.InvalidValueException;

public class GroupHostAbsentException extends InvalidValueException {

	public GroupHostAbsentException(String message) {
		super(message, ErrorCode.GROUP_HOST_ABSENT);
	}
}
