package podo.odeego.domain.member.exception;

import podo.odeego.web.error.ErrorCode;
import podo.odeego.web.error.exception.ResourceNotFoundException;

public class MemberNotFoundException extends ResourceNotFoundException {

	public MemberNotFoundException(String message) {
		super(message, ErrorCode.MEMBER_NOT_FOUND);
	}
}
