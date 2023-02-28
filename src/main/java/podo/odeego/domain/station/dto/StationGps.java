package podo.odeego.domain.station.dto;

import podo.odeego.domain.station.entity.Station;

public class StationGps {

	private String stationName;
	private Double lat;
	private Double lng;

	private StationGps() {
	}

	public static StationGps from(Station station) {
		StationGps dto = new StationGps();

		if (station == null) {
			dto.stationName = "";
			dto.lat = 0.0;
			dto.lng = 0.0;
			return dto;
		}

		dto.stationName = station.name();
		dto.lat = station.latitude();
		dto.lng = station.longitude();
		return dto;
	}

	public String getStationName() {
		return stationName;
	}

	public Double getLat() {
		return lat;
	}

	public Double getLng() {
		return lng;
	}
}
