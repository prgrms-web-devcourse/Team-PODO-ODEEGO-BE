package podo.odeego.global.error.exception;

import podo.odeego.global.error.ErrorCode;

public class AuthenticationException extends RuntimeException {

	private final ErrorCode errorCode;

	public AuthenticationException(String message){
		this(message, ErrorCode.UNAUTHORIZED);
	}

	public AuthenticationException(String message, ErrorCode errorCode){
		super(message);
		this.errorCode = errorCode;
	}

	public ErrorCode errorCode(){
		return errorCode;
	}
}
