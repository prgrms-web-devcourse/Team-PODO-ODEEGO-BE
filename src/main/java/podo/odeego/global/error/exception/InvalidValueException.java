package podo.odeego.global.error.exception;

import podo.odeego.global.error.ErrorCode;

public class InvalidValueException extends BusinessException {

	public InvalidValueException(String message) {
		super(message, ErrorCode.INVALID_INPUT_VALUE);
	}

	public InvalidValueException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}
}
