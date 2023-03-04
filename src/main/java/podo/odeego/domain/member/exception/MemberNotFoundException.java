package podo.odeego.domain.member.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.ResourceNotFoundException;

public class MemberNotFoundException extends ResourceNotFoundException {

	public MemberNotFoundException(String message) {
		super(message, ErrorCode.MEMBER_NOT_FOUND);
	}
}
