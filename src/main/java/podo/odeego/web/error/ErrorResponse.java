package podo.odeego.web.error;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public record ErrorResponse(
	HttpStatus status,
	String errorMessage,
	List<FieldError> errors
) {

	public static ErrorResponse of(ErrorCode errorCode) {
		return new ErrorResponse(errorCode.status(), errorCode.message(), new ArrayList<>());
	}

	public static ErrorResponse of(ErrorCode errorCode, List<FieldError> errors) {
		return new ErrorResponse(errorCode.status(), errorCode.message(), errors);
	}

	public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
		String value = e.getValue() == null ? "" : e.getValue().toString();
		List<FieldError> errors = ErrorResponse.FieldError.of(e.getName(), value, e.getErrorCode());
		return ErrorResponse.of(ErrorCode.INVALID_TYPE_VALUE, errors);
	}

	private record FieldError(
		String field,
		String value,
		String reason
	) {

		public static List<FieldError> of(String field, String value, String reason) {
			List<FieldError> fieldErrors = new ArrayList<>();
			fieldErrors.add(new FieldError(field, value, reason));
			return fieldErrors;
		}
	}
}
