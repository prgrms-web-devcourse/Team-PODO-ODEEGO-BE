package podo.odeego.domain.member.dto;

import javax.validation.constraints.NotBlank;

public record MemberSignUpRequest(

	@NotBlank
	String nickname,

	@NotBlank
	String defaultStationName
) {
}
