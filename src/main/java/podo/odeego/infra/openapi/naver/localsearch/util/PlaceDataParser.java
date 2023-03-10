package podo.odeego.infra.openapi.naver.localsearch.util;

public class PlaceDataParser {

	private static final String BOLD_OPEN_TAG = "<b>";
	private static final String BOLD_CLOSE_TAG = "</b>";
	private static final String AMPERSAND = "&amp;";

	private PlaceDataParser() {
	}

	public static String trimHtmlTags(String target) {
		return target
			.replaceAll(BOLD_OPEN_TAG, "")
			.replaceAll(BOLD_CLOSE_TAG, "")
			.replaceAll(AMPERSAND, "");
	}
}
