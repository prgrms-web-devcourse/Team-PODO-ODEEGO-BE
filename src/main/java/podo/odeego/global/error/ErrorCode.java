package podo.odeego.global.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	// Common
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "400", "Invalid Input Value."),
	INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "400", "Invalid Type Value."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401", "Unauthorized."),
	FORBIDDEN(HttpStatus.FORBIDDEN, "403", "Forbidden."),
	NOT_FOUND(HttpStatus.NOT_FOUND, "404", "Not Found."),
	NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE, "406", "Not Acceptable."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "Internal Server Error."),

	// Auth
	TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "A001", "Token not found in request header."),
	NOT_GRANTED_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "A002", "Not granted token type."),
	EXPIRED_JWT(HttpStatus.UNAUTHORIZED, "A003", "Jwt is expired."),
	INVALID_JWT(HttpStatus.UNAUTHORIZED, "A004", "Invalid Jwt."),

	// Member
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "Member Not Found."),
	MEMBER_NICKNAME_DUPLICATED(HttpStatus.BAD_REQUEST, "M002", "Member nickname is duplicated."),
	MEMBER_NICKNAME_OUT_OF_BOUNDS(HttpStatus.BAD_REQUEST, "M003", "Member nickname length is out of bounds."),
	MEMBER_NICKNAME_UNFORMATTED(HttpStatus.BAD_REQUEST, "M004", "Member nickname is unformatted."),
	DEFAULT_STATION_NOT_EXISTS(HttpStatus.BAD_REQUEST, "M005", "Station doesn't exists in our service."),

	// Group
	GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "G001", "Group Not Found."),
	MEMBER_ALREADY_PARTICIPATING_IN_GROUP(HttpStatus.BAD_REQUEST, "G002", "Member is already participating group."),
	GROUP_ALREADY_FULL(HttpStatus.BAD_REQUEST, "G003", "Group is already full."),
	GROUP_HOST_ABSENT(HttpStatus.INTERNAL_SERVER_ERROR, "G004", "Group host is absent."),
	GROUP_CAPACITY_OUT_OF_BOUNDS(HttpStatus.BAD_REQUEST, "G005", "Group capacity is out of bounds."),
	GROUP_MEMBER_STATION_ALREADY_DEFINED(HttpStatus.BAD_REQUEST, "G006", "Group Member's station is already defined."),

	// Station
	STATION_NOT_FOUND(HttpStatus.NOT_FOUND, "S001", "Station Not Found."),

	// Place
	PlACE_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "Place Not Found.");

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
