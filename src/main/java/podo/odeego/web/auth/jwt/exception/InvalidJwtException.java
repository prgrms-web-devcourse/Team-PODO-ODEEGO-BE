package podo.odeego.web.auth.jwt.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.AuthenticationException;

public class InvalidJwtException extends AuthenticationException {

	public InvalidJwtException(String message) {
		super(message, ErrorCode.INVALID_JWT);
	}
}
