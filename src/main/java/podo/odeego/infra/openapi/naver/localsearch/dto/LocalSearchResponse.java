package podo.odeego.infra.openapi.naver.localsearch.dto;

import java.util.List;

import podo.odeego.domain.place.dto.PlaceSimpleResponse;
import podo.odeego.infra.openapi.naver.localsearch.util.PlaceDataParser;

public record LocalSearchResponse(
	String lastBuildDate,
	int total,
	int start,
	int display,
	List<LocalSearchItem> items
) {

	@Override
	public String toString() {
		return System.lineSeparator() +
			"LocalSearchResponse{" + System.lineSeparator() +
			"	lastBuildDate='" + lastBuildDate + '\'' + "," + System.lineSeparator() +
			"	total=" + total + "," + System.lineSeparator() +
			"	start=" + start + "," + System.lineSeparator() +
			"	display=" + display + "," + System.lineSeparator() +
			"	items=" + items + System.lineSeparator() +
			'}';
	}

	public List<PlaceSimpleResponse> getPlaces() {
		return this.items.stream()
			.map(item -> new PlaceSimpleResponse(PlaceDataParser.trimHtmlTags(item.title), item.roadAddress))
			.toList();
	}

	private record LocalSearchItem(
		String title,
		String link,
		String category,
		String description,
		String telephone,
		String address,
		String roadAddress,
		int mapx,
		int mapy
	) {

		@Override
		public String toString() {
			return System.lineSeparator() +
				"		LocalSearchItem{" + System.lineSeparator() +
				"			title='" + title + '\'' + "," + System.lineSeparator() +
				"			link='" + link + '\'' + "," + System.lineSeparator() +
				"			category='" + category + '\'' + "," + System.lineSeparator() +
				"			description='" + description + '\'' + "," + System.lineSeparator() +
				"			telephone='" + telephone + '\'' + "," + System.lineSeparator() +
				"			address='" + address + '\'' + "," + System.lineSeparator() +
				"			roadAddress='" + roadAddress + '\'' + "," + System.lineSeparator() +
				"			mapx=" + mapx + "," + System.lineSeparator() +
				"			mapy=" + mapy + System.lineSeparator() +
				"		}";
		}
	}
}
