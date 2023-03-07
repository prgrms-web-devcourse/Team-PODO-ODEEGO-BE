package podo.odeego.global.error;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public class ErrorResponse {

	private HttpStatus status;
	private String errorCode;
	private String errorMessage;
	private List<FieldError> errors;

	private ErrorResponse(ErrorCode errorCode) {
		this(errorCode, new ArrayList<>());
	}

	private ErrorResponse(ErrorCode errorCode, List<FieldError> errors) {
		this.status = errorCode.status();
		this.errorCode = errorCode.errorCode();
		this.errorMessage = errorCode.message();
		this.errors = errors;
	}

	private ErrorResponse(ErrorCode errorCode, String message) {
		this.status = errorCode.status();
		this.errorCode = errorCode.errorCode();
		this.errorMessage = message;
		this.errors = new ArrayList<>();
	}

	public static ErrorResponse of(ErrorCode errorCode) {
		return new ErrorResponse(errorCode);
	}

	public static ErrorResponse of(ErrorCode errorCode, String message) {
		return new ErrorResponse(errorCode, message);
	}

	public static ErrorResponse of(ErrorCode errorCode, List<FieldError> errors) {
		return new ErrorResponse(errorCode, errors);
	}

	public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult) {
		return new ErrorResponse(errorCode, FieldError.of(bindingResult));
	}

	public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
		String value = e.getValue() == null ? "" : e.getValue().toString();
		List<FieldError> errors = ErrorResponse.FieldError.of(e.getName(), value, e.getErrorCode());
		return ErrorResponse.of(ErrorCode.INVALID_TYPE_VALUE, errors);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public List<FieldError> getErrors() {
		return errors;
	}

	private static class FieldError {

		private String field;
		private String value;
		private String reason;

		private FieldError() {
		}

		public static List<FieldError> of(String field, String value, String reason) {
			FieldError error = new FieldError();

			error.field = field;
			error.value = value;
			error.reason = reason;

			return List.of(error);
		}

		private static List<FieldError> of(BindingResult bindingResult) {
			return bindingResult.getFieldErrors()
				.stream()
				.map(FieldError::of)
				.toList();
		}

		private static FieldError of(org.springframework.validation.FieldError fieldError) {
			FieldError error = new FieldError();

			error.field = fieldError.getField();
			error.value = fieldError.getRejectedValue() == null ? "" : fieldError.getRejectedValue().toString();
			error.reason = fieldError.getDefaultMessage();

			return error;
		}

		public String getField() {
			return field;
		}

		public String getValue() {
			return value;
		}

		public String getReason() {
			return reason;
		}
	}
}
