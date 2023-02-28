package podo.odeego.domain.group.exception;

import podo.odeego.web.error.ErrorCode;
import podo.odeego.web.error.exception.BusinessException;

public class GroupAlreadyContainsException extends BusinessException {
	public GroupAlreadyContainsException(String message) {
		super(message, ErrorCode.FORBIDDEN);
	}
}
