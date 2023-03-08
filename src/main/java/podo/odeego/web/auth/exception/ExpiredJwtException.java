package podo.odeego.web.auth.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.AuthenticationException;

public class ExpiredJwtException extends AuthenticationException {

	public ExpiredJwtException(String message) {
		super(message, ErrorCode.EXPIRED_JWT);
	}
}
