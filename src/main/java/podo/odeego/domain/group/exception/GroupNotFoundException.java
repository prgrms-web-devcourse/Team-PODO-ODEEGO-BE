package podo.odeego.domain.group.exception;

import podo.odeego.web.error.exception.ResourceNotFoundException;

public class GroupNotFoundException extends ResourceNotFoundException {

	public GroupNotFoundException(String message) {
		super(message);
	}
}
