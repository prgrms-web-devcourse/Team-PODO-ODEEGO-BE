package podo.odeego.domain.group.exception;

import podo.odeego.web.error.ErrorCode;
import podo.odeego.web.error.exception.InvalidValueException;

public class GroupHostAbsentException extends InvalidValueException {

	public GroupHostAbsentException(String message) {
		super(message, ErrorCode.GROUP_HOST_ABSENT);
	}
}
