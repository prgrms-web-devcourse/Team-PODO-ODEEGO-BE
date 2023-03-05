package podo.odeego.infra.openapi.naver.localsearch.dto;

import static podo.odeego.infra.openapi.naver.localsearch.dto.LocalSearchResponse.*;

import podo.odeego.infra.openapi.naver.localsearch.util.PlaceDataParser;

public class PlaceQueryDto {

	private final String businessName;
	private final String roadAddress;

	private PlaceQueryDto(String businessName, String roadAddress) {
		this.businessName = businessName;
		this.roadAddress = roadAddress;
	}

	public static PlaceQueryDto of(LocalSearchItem localSearchItem) {
		return new PlaceQueryDto(
			PlaceDataParser.trimHtmlTags(localSearchItem.title()),
			localSearchItem.roadAddress()
		);
	}

	public String getBusinessName() {
		return businessName;
	}

	public String getRoadAddress() {
		return roadAddress;
	}
}
