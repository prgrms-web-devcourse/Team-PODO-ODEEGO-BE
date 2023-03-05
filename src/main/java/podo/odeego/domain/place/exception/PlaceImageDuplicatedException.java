package podo.odeego.domain.place.exception;

import podo.odeego.global.error.exception.InvalidValueException;

public class PlaceImageDuplicatedException extends InvalidValueException {

	public PlaceImageDuplicatedException(String message) {
		super(message);
	}
}
