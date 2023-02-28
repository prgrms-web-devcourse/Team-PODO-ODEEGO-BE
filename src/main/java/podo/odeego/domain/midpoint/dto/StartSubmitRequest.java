package podo.odeego.domain.midpoint.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record StartSubmitRequest(

	@NotNull
	UUID groupId,

	@NotBlank
	String stationName,

	double lat,

	double lng
) {
}
