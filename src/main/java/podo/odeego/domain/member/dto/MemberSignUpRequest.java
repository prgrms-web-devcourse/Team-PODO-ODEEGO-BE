package podo.odeego.domain.member.dto;

import javax.validation.constraints.NotBlank;

import podo.odeego.domain.util.JsonUtils;

public record MemberSignUpRequest(

	@NotBlank
	String nickname,

	@NotBlank
	String defaultStationName
) {

	@Override
	public String defaultStationName() {
		return JsonUtils.getStationNameWithoutLine(defaultStationName);
	}
}
