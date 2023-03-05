package podo.odeego.domain.group.exception;

import podo.odeego.global.error.ErrorCode;
import podo.odeego.global.error.exception.BusinessException;

public class GroupMemberStationAlreadyDefinedException extends BusinessException {
	public GroupMemberStationAlreadyDefinedException(String message) {
		super(message, ErrorCode.GROUP_MEMBER_STATION_ALREADY_DEFINED);
	}
}
