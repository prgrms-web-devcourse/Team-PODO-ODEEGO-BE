package podo.odeego.domain.member.entity;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.BusinessException;

public class MemberAlreadyRegularException extends BusinessException {

	public MemberAlreadyRegularException(String message) {
		super(message, ErrorCode.MEMBER_ALREADY_REGULAR);
	}
}
