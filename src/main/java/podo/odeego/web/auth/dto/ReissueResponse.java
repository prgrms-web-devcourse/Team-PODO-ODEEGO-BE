package podo.odeego.web.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record ReissueResponse(
	String accessToken,
	@JsonIgnore
	String refreshToken
) {
}
