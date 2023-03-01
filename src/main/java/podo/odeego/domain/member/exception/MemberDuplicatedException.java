package podo.odeego.domain.member.exception;

import podo.odeego.web.error.ErrorCode;
import podo.odeego.web.error.exception.InvalidValueException;

public class MemberDuplicatedException extends InvalidValueException {

	public MemberDuplicatedException(String message) {
		super(message, ErrorCode.MEMBER_DUPLICATED);
	}
}
