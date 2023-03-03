package podo.odeego.domain.group.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.ResourceNotFoundException;

public class GroupNotFoundException extends ResourceNotFoundException {

	public GroupNotFoundException(String message) {
		super(message, ErrorCode.GROUP_NOT_FOUND);
	}
}
