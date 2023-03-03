package podo.odeego.web.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	// Common
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "400", "Invalid Input Value."),
	INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "400", "Invalid Type Value."),
	FORBIDDEN(HttpStatus.FORBIDDEN, "403", "Forbidden."),
	NOT_FOUND(HttpStatus.NOT_FOUND, "404", "Not Found."),
	NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE, "406", "Not Acceptable."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "Internal Server Error."),

	// Member
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M404", "Member Not Found."),
	MEMBER_NICKNAME_DUPLICATED(HttpStatus.BAD_REQUEST, "M001", "Member nickname is duplicated."),

	// Group
	GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "G404", "Group Not Found."),
	MEMBER_ALREADY_PARTICIPATING_IN_GROUP(HttpStatus.BAD_REQUEST, "G001", "Member is already participating group."),
	GROUP_ALREADY_FULL(HttpStatus.BAD_REQUEST, "G002", "Group is already full."),
	GROUP_HOST_ABSENT(HttpStatus.INTERNAL_SERVER_ERROR, "G003", "Group host is absent.");

	private final HttpStatus status;
	private final String errorCode;
	private final String message;

	ErrorCode(HttpStatus status, String errorCode, String message) {
		this.status = status;
		this.errorCode = errorCode;
		this.message = message;
	}

	public HttpStatus status() {
		return status;
	}

	public String errorCode() {
		return errorCode;
	}

	public String message() {
		return message;
	}
}
