package podo.odeego.global.error.exception;

import podo.odeego.global.error.ErrorCode;

public class AlreadyFullException extends BusinessException {

	public AlreadyFullException(String message) {
		this(message, ErrorCode.FORBIDDEN);
	}

	public AlreadyFullException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}
}
