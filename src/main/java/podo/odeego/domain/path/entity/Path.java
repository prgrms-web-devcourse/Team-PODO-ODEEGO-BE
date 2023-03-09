package podo.odeego.domain.path.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import podo.odeego.domain.station.entity.Station;

@Entity
public class Path {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "path_id")
	private Long id;

	@Column(nullable = false)
	private String startStation;

	@Column(nullable = false)
	private String endStation;

	private int requiredTime;

	@Column(nullable = false, columnDefinition = "LONGTEXT")
	private String stations;

	protected Path() {
	}

	private Path(Long id, String startStation, String endStation, int requiredTime, String stations) {
		this.id = id;
		this.startStation = startStation;
		this.endStation = endStation;
		this.requiredTime = requiredTime;
		this.stations = stations;
	}

	public static Path forTest(String start, String end, int requiredTime, String path) {
		return new Path(null, start, end, requiredTime, path);
	}

	public boolean isEnd(Station station) {
		return Objects.equals(endStation, station.name());
	}

	public String startStation() {
		return startStation;
	}

	public String endStation() {
		return endStation;
	}

	public int requiredTime() {
		return requiredTime;
	}

	public String stations() {
		return stations;
	}
}
