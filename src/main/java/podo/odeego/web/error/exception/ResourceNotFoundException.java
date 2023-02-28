package podo.odeego.web.error.exception;

import podo.odeego.web.error.ErrorCode;

public class ResourceNotFoundException extends BusinessException {

	public ResourceNotFoundException(String message) {
		super(message, ErrorCode.NOT_FOUND);
	}
}
