package podo.odeego.web.auth.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.AuthenticationException;

public class TokenTypeNotGrantedException extends AuthenticationException {

	public TokenTypeNotGrantedException(String message) {
		super(message, ErrorCode.NOT_GRANTED_TOKEN_TYPE);
	}
}
