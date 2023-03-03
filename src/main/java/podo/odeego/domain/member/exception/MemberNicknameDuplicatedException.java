package podo.odeego.domain.member.exception;

import podo.odeego.web.error.ErrorCode;
import podo.odeego.web.error.exception.InvalidValueException;

public class MemberNicknameDuplicatedException extends InvalidValueException {

	public MemberNicknameDuplicatedException(String message) {
		super(message, ErrorCode.INVALID_INPUT_VALUE);
	}
}
