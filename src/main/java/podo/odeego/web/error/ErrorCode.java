package podo.odeego.web.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	// Common
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Invalid Input Value"),
	INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "Invalid Type Value"),
	NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found Error"),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error"),
	GROUP_HOST_ABSENT(HttpStatus.INTERNAL_SERVER_ERROR, "Group Host Absent"),
	FORBIDDEN(HttpStatus.FORBIDDEN, "Forbidden"),
	NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE, "Not Acceptable");

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
