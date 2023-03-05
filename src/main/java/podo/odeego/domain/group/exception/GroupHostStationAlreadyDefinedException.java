package podo.odeego.domain.group.exception;

import podo.odeego.web.error.ErrorCode;
import podo.odeego.web.error.exception.BusinessException;

public class GroupHostStationAlreadyDefinedException extends BusinessException {
	public GroupHostStationAlreadyDefinedException(String message) {
		super(message, ErrorCode.NOT_ACCEPTABLE);
	}
}
