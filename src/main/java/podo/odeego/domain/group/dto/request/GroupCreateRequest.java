package podo.odeego.domain.group.dto.request;

import javax.validation.constraints.NotNull;

public record GroupCreateRequest(

	@NotNull
	Long capacity
) {
}
