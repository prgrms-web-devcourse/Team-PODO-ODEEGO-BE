package podo.odeego.infra.openapi.naver.util;

public class PlaceDataParser {

	private static final String BOLD_OPEN_TAG = "<b>";
	private static final String BOLD_CLOSE_TAG = "</b>";

	private PlaceDataParser() {
	}

	public static String trimHtmlTags(String target) {
		return target
			.replaceAll(BOLD_OPEN_TAG, "")
			.replaceAll(BOLD_CLOSE_TAG, "");
	}
}
