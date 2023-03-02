package podo.odeego.web.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	// Common
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Invalid Input Value"),
	INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "Invalid Type Value"),
	FORBIDDEN(HttpStatus.FORBIDDEN, "Forbidden"),
	NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found Error"),
	NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE, "Not Acceptable"),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error"),

	// Member
	MEMBER_DUPLICATED(HttpStatus.BAD_REQUEST, "Member Duplicated"),

	// Group
	GROUP_HOST_ABSENT(HttpStatus.INTERNAL_SERVER_ERROR, "Group Host Absent");

	private final HttpStatus status;
	private final String message;

	ErrorCode(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public HttpStatus status() {
		return status;
	}

	public String message() {
		return message;
	}
}
