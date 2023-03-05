package podo.odeego.domain.group.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.AlreadyFullException;

public class GroupAlreadyFullException extends AlreadyFullException {

	public GroupAlreadyFullException(String message) {
		super(message, ErrorCode.GROUP_ALREADY_FULL);
	}
}
