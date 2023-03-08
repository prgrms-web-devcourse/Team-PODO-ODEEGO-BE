package podo.odeego.domain.place.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.ResourceNotFoundException;

public class PlaceNotFoundException extends ResourceNotFoundException {

	public PlaceNotFoundException(String message) {
		super(message, ErrorCode.PlACE_NOT_FOUND);
	}
}
