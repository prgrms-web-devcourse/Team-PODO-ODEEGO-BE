package podo.odeego.domain.member.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.InvalidValueException;

public class MemberNicknameOutOfBoundsException extends InvalidValueException {

	public MemberNicknameOutOfBoundsException(String message) {
		super(message, ErrorCode.MEMBER_NICKNAME_OUT_OF_BOUNDS);
	}
}
