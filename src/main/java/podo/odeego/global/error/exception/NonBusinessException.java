package podo.odeego.global.error.exception;

import podo.odeego.global.error.ErrorCode;

public class NonBusinessException extends RuntimeException {

	private final ErrorCode errorCode;

	public NonBusinessException(String message) {
		this(message, ErrorCode.INTERNAL_SERVER_ERROR);
	}

	public NonBusinessException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public ErrorCode errorCode() {
		return errorCode;
	}
}
