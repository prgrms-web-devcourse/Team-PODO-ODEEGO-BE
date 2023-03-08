package podo.odeego.web.api.scraping.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.NonBusinessException;

public class NotAuthorizedException extends NonBusinessException {

	public NotAuthorizedException(String message) {
		super(message, ErrorCode.UNAUTHORIZED);
	}
}
