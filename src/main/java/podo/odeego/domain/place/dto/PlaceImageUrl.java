package podo.odeego.domain.place.dto;

import java.util.List;

import podo.odeego.domain.place.entity.PlaceImage;

public record PlaceImageUrl(
	String url
) {

	public static List<PlaceImageUrl> from(List<PlaceImage> images) {
		return images.stream()
			.map(image -> new PlaceImageUrl(image.getUrl()))
			.toList();
	}
}
