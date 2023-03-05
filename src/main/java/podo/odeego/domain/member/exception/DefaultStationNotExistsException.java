package podo.odeego.domain.member.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.InvalidValueException;

public class DefaultStationNotExistsException extends InvalidValueException {
	public DefaultStationNotExistsException(String message) {
		super(message, ErrorCode.DEFAULT_STATION_NOT_EXISTS);
	}
}
