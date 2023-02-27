package podo.odeego.domain.member.exception;

import podo.odeego.web.error.exception.ResourceNotFoundException;

public class MemberNotFoundException extends ResourceNotFoundException {

	public MemberNotFoundException(String message) {
		super(message);
	}
}
