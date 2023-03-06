package podo.odeego.domain.util;

public class JsonUtils {

	private static final int STATION_NAME_INDEX = 0;

	public static String getStationNameWithoutLine(String stationInfo) {
		if (stationInfo.endsWith("호선")) {
			return stationInfo.split(" ")[STATION_NAME_INDEX];
		}

		return stationInfo;
	}
}
