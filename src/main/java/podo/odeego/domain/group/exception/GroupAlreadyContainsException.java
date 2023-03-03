package podo.odeego.domain.group.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.BusinessException;

public class GroupAlreadyContainsException extends BusinessException {

	public GroupAlreadyContainsException(String message) {
		super(message, ErrorCode.MEMBER_ALREADY_PARTICIPATING_IN_GROUP);
	}
}
