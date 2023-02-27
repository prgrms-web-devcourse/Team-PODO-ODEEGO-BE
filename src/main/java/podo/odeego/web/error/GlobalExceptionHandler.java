package podo.odeego.web.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import podo.odeego.web.error.exception.BusinessException;
import podo.odeego.web.error.exception.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException e
	) {
		log.info("handleMethodArgumentTypeMismatchException", e);
		final ErrorResponse response = ErrorResponse.of(e);
		return newResponseEntity(response);
	}

	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
		log.info("handleRuntimeException", e);
		ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
		return newResponseEntity(response);
	}

	@ExceptionHandler(BusinessException.class)
	protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
		log.info("handleBusinessException", e);
		ErrorResponse response = ErrorResponse.of(e.errorCode());
		return newResponseEntity(response);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	protected ResponseEntity<ErrorResponse> handleBusinessException(ResourceNotFoundException e) {
		log.info("handleEntityNotFoundException", e);
		ErrorResponse response = ErrorResponse.of(e.errorCode());
		return newResponseEntity(response);
	}

	private ResponseEntity<ErrorResponse> newResponseEntity(ErrorResponse response) {
		return new ResponseEntity<>(response, response.status());
	}
}
