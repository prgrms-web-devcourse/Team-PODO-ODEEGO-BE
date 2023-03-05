package podo.odeego.domain.place.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.InvalidValueException;

public class PlaceCategoryNotValidException extends InvalidValueException {

	public PlaceCategoryNotValidException(String message) {
		super(message, ErrorCode.INTERNAL_SERVER_ERROR);
	}
}
