package podo.odeego.web.error.exception;

import podo.odeego.web.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

	public EntityNotFoundException(String message) {
		super(message, ErrorCode.NOT_FOUND);
	}
}
