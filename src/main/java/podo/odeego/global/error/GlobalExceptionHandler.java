package podo.odeego.global.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import podo.odeego.global.error.exception.AuthenticationException;
import podo.odeego.global.error.exception.BusinessException;
import podo.odeego.global.error.exception.NonBusinessException;
import podo.odeego.infra.openapi.kakao.exception.KakaoClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error("handleMethodArgumentNotValidException", e);
		ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
		return newResponseEntity(response);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException e
	) {
		log.info("handleMethodArgumentTypeMismatchException", e);
		final ErrorResponse response = ErrorResponse.of(e);
		return newResponseEntity(response);
	}

	@ExceptionHandler(KakaoClientErrorException.class)
	protected ResponseEntity<ErrorResponse> handleKakaoClientErrorException(
		KakaoClientErrorException e
	) {
		log.info("handleKakaoClientErrorException", e);
		final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getMessage());
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

	@ExceptionHandler(NonBusinessException.class)
	protected ResponseEntity<ErrorResponse> handleNonBusinessException(NonBusinessException e) {
		log.info("handleNonBusinessException", e);
		ErrorResponse response = ErrorResponse.of(e.errorCode());
		return newResponseEntity(response);
	}

	@ExceptionHandler(AuthenticationException.class)
	protected ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
		log.info("handleAuthenticationException", e);
		ErrorResponse response = ErrorResponse.of(e.errorCode());
		return newResponseEntity(response);
	}

	private ResponseEntity<ErrorResponse> newResponseEntity(ErrorResponse response) {
		return new ResponseEntity<>(response, response.getStatus());
	}
}
