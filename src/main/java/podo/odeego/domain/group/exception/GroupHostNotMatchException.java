package podo.odeego.domain.group.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.InvalidValueException;

public class GroupHostNotMatchException extends InvalidValueException {

	public GroupHostNotMatchException(String message) {
		super(message, ErrorCode.GROUP_HOST_NOT_MATCH);
	}
}
