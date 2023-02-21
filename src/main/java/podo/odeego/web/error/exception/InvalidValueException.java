package podo.odeego.web.error.exception;

import podo.odeego.web.error.ErrorCode;

public class InvalidValueException extends BusinessException {

	public InvalidValueException(String message) {
		super(message, ErrorCode.INVALID_INPUT_VALUE);
	}
}
