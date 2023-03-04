package podo.odeego.domain.station.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.ResourceNotFoundException;

public class StationNotFoundException extends ResourceNotFoundException {

	public StationNotFoundException(String message) {
		super(message, ErrorCode.STATION_NOT_FOUND);
	}
}
