package podo.odeego.domain.group.exception;

import podo.odeego.web.error.exception.AlreadyFullException;

public class GroupAlreadyFullException extends AlreadyFullException {

	public GroupAlreadyFullException(String message) {
		super(message);
	}
}
