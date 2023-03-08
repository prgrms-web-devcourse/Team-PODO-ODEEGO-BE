package podo.odeego.domain.place.dto;

import java.util.List;

public record PlaceImageAddRequest(
	Long placeId,
	List<PlaceImageCreateRequest> imageCreateRequests
) {
}
