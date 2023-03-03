package podo.odeego.domain.group.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.InvalidValueException;

public class AlreadyParticipatingGroupException extends InvalidValueException {

	public AlreadyParticipatingGroupException(String message) {
		super(message, ErrorCode.MEMBER_ALREADY_PARTICIPATING_IN_GROUP);
	}
}
