package podo.odeego.domain.member.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.InvalidValueException;

public class MemberNicknameDuplicatedException extends InvalidValueException {

	public MemberNicknameDuplicatedException(String message) {
		super(message, ErrorCode.MEMBER_NICKNAME_DUPLICATED);
	}
}
