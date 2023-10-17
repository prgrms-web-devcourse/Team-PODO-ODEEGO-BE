package podo.odeego.domain.refreshtoken.exception;

public class WrongRefreshTokenException extends IllegalArgumentException {

	public WrongRefreshTokenException(String message) {
		super(message);
	}
}
