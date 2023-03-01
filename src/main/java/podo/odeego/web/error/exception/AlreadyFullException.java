package podo.odeego.web.error.exception;

import podo.odeego.web.error.ErrorCode;

public class AlreadyFullException extends BusinessException {

	public AlreadyFullException(String message) {
		super(message, ErrorCode.FORBIDDEN);
	}
}
