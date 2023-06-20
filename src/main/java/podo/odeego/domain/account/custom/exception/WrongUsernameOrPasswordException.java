package podo.odeego.domain.account.custom.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.InvalidValueException;

public class WrongUsernameOrPasswordException extends InvalidValueException {

	public WrongUsernameOrPasswordException(String message) {
		super(message, ErrorCode.INVALID_INPUT_VALUE);
	}
}
