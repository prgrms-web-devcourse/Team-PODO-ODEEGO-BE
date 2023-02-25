package podo.odeego.infra.openapi.naver.imagesearch.dto;

import java.util.List;

public record ImageSearchResponse(
	String lastBuildDate,
	int total,
	int start,
	int display,
	List<ImageSearchItem> items
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

	private record ImageSearchItem(
		String title,
		String link,
		String thumbnail,
		String sizeheigt,
		String sizewidth
	) {

		@Override
		public String toString() {
			return System.lineSeparator() +
				"		ImageSearchItem{" + System.lineSeparator() +
				"			title='" + title + '\'' + "," + System.lineSeparator() +
				"			link='" + link + '\'' + "," + System.lineSeparator() +
				"			thumbnail='" + thumbnail + '\'' + "," + System.lineSeparator() +
				"			sizeheigt='" + sizeheigt + '\'' + "," + System.lineSeparator() +
				"			sizewidth='" + sizewidth + '\'' + System.lineSeparator() +
				"		}";
		}
	}
}
