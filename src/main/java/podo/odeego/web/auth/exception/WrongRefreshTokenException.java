package podo.odeego.web.auth.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.AuthenticationException;

public class WrongRefreshTokenException extends AuthenticationException {

	public WrongRefreshTokenException(String message) {
		super(message, ErrorCode.WRONG_REFRESH_TOKEN);
	}
}
