package podo.odeego.web.auth.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.AuthenticationException;

public class TokenNotFoundException extends AuthenticationException {

	public TokenNotFoundException(String message) {
		super(message, ErrorCode.TOKEN_NOT_FOUND);
	}
}
