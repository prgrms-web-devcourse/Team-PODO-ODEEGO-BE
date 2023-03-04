package podo.odeego.global.error.exception;

import podo.odeego.global.error.ErrorCode;

public class BusinessException extends RuntimeException {

	private final ErrorCode errorCode;

	public BusinessException(String message) {
		this(message, ErrorCode.INTERNAL_SERVER_ERROR);
	}

	public BusinessException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public ErrorCode errorCode() {
		return errorCode;
	}
}
