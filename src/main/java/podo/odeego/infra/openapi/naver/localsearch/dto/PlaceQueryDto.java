package podo.odeego.infra.openapi.naver.localsearch.dto;

import static podo.odeego.infra.openapi.naver.localsearch.dto.LocalSearchResponse.*;

import java.util.List;
import java.util.Objects;

import podo.odeego.infra.openapi.naver.localsearch.util.PlaceDataParser;

public class PlaceQueryDto {

	private final String businessName;
	private final String roadAddress;

	private PlaceQueryDto(String businessName, String roadAddress) {
		this.businessName = businessName;
		this.roadAddress = roadAddress;
	}

	public static PlaceQueryDto from(LocalSearchItem localSearchItem) {
		return new PlaceQueryDto(
			PlaceDataParser.trimHtmlTags(localSearchItem.title()),
			localSearchItem.roadAddress()
		);
	}

	public static List<PlaceQueryDto> from(LocalSearchResponse localSearchResponse) {
		return localSearchResponse.items()
			.stream()
			.map(PlaceQueryDto::from)
			.toList();
	}

	public String businessName() {
		return businessName;
	}

	public String roadAddress() {
		return roadAddress;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PlaceQueryDto that = (PlaceQueryDto)o;
		return Objects.equals(businessName(), that.businessName()) && Objects.equals(
			roadAddress(), that.roadAddress());
	}

	@Override
	public int hashCode() {
		return Objects.hash(businessName(), roadAddress());
	}

	@Override
	public String toString() {
		return "PlaceQueryDto{" +
			"businessName='" + businessName + '\'' +
			", roadAddress='" + roadAddress + '\'' +
			'}';
	}
}
