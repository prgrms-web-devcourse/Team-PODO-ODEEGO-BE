package podo.odeego.infra.openapi.naver.dto;

import java.util.List;

public class LocalSearchResponse {

	private String lastBuildDate;
	private int total;
	private int start;
	private int display;
	private List<LocalSearchItem> items;

	public LocalSearchResponse() {
	}

	public LocalSearchResponse(String lastBuildDate, int total, int start, int display, List<LocalSearchItem> items) {
		this.lastBuildDate = lastBuildDate;
		this.total = total;
		this.start = start;
		this.display = display;
		this.items = items;
	}

	public String getLastBuildDate() {
		return lastBuildDate;
	}

	public int getTotal() {
		return total;
	}

	public int getStart() {
		return start;
	}

	public int getDisplay() {
		return display;
	}

	public List<LocalSearchItem> getItems() {
		return items;
	}

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

	private static class LocalSearchItem {

		private String title;
		private String link;
		private String category;
		private String description;
		private String telephone;
		private String address;
		private String roadAddress;
		private int mapx;
		private int mapy;

		public LocalSearchItem() {
		}

		public LocalSearchItem(String title, String link, String category, String description, String telephone,
			String address, String roadAddress, int mapx, int mapy) {
			this.title = title;
			this.link = link;
			this.category = category;
			this.description = description;
			this.telephone = telephone;
			this.address = address;
			this.roadAddress = roadAddress;
			this.mapx = mapx;
			this.mapy = mapy;
		}

		public String getTitle() {
			return title;
		}

		public String getLink() {
			return link;
		}

		public String getCategory() {
			return category;
		}

		public String getDescription() {
			return description;
		}

		public String getTelephone() {
			return telephone;
		}

		public String getAddress() {
			return address;
		}

		public String getRoadAddress() {
			return roadAddress;
		}

		public int getMapx() {
			return mapx;
		}

		public int getMapy() {
			return mapy;
		}

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
