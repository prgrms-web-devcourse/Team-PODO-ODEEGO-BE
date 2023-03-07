package podo.odeego.infra.openapi.naver.imagesearch.dto;

import java.util.List;

import podo.odeego.domain.place.dto.PlaceImageCreateRequest;

public class ImageQueryDto {

	private final String source;
	private final String url;

	private ImageQueryDto(String source, String url) {
		this.source = source;
		this.url = url;
	}

	public static ImageQueryDto from(ImageSearchResponse.ImageSearchItem imageSearchItem) {
		return new ImageQueryDto(imageSearchItem.title(), imageSearchItem.link());
	}

	public static List<ImageQueryDto> from(ImageSearchResponse imageSearchResponse) {
		return imageSearchResponse.items()
			.stream()
			.map(ImageQueryDto::from)
			.toList();
	}

	public static List<PlaceImageCreateRequest> toPlaceImageCreateRequests(List<ImageQueryDto> dtos) {
		return dtos.stream()
			.map(ImageQueryDto::toPlaceImageCreateRequest)
			.toList();
	}

	public PlaceImageCreateRequest toPlaceImageCreateRequest() {
		return new PlaceImageCreateRequest(source, url);
	}

	@Override
	public String toString() {
		return "ImageQueryDto{" +
			"source='" + source + '\'' +
			", url='" + url + '\'' +
			'}';
	}
}
