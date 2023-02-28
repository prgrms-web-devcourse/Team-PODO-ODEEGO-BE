package podo.odeego.domain.group.exception;

import podo.odeego.web.error.exception.InvalidValueException;

public class AlreadyParticipatingGroupException extends InvalidValueException {

	public AlreadyParticipatingGroupException(String message) {
		super(message);
	}
}
