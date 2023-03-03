package podo.odeego.web.error.exception;

import podo.odeego.web.error.ErrorCode;

public class AlreadyFullException extends BusinessException {

	public AlreadyFullException(String message) {
		this(message, ErrorCode.FORBIDDEN);
	}

	public AlreadyFullException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}
}
