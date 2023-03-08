package podo.odeego.infra.openapi.kakao.exception;

public class KakaoClientErrorException extends RuntimeException {

	public KakaoClientErrorException(String message) {
		super(message);
	}
}
