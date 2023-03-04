package podo.odeego.domain.member.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.InvalidValueException;

public class MemberNicknameUnformattedException extends InvalidValueException {

	public MemberNicknameUnformattedException(String message) {
		super(message, ErrorCode.MEMBER_NICKNAME_UNFORMATTED);
	}
}
