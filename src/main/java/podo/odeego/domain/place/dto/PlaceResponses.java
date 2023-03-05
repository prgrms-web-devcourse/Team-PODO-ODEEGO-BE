package podo.odeego.domain.place.dto;

import java.util.List;

public record PlaceResponses(
	List<PlaceSimpleResponse> places
) {
}
