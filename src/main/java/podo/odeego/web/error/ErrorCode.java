package podo.odeego.web.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	// Common
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Invalid Input Value."),
	INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "Invalid Type Value."),
	NO_RESOURCE(HttpStatus.NOT_FOUND, "No Resource Exists."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error.");

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
