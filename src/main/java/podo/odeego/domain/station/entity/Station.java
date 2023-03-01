package podo.odeego.domain.station.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Station {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "station_id")
	private Long id;

	@Column(nullable = false, length = 15)
	private String name;

	private String address;

	private double latitude;

	private double longitude;

	@Column(nullable = false, length = 10)
	private String line;

	protected Station() {
	}

	public Station(String name, String address, double latitude, double longitude, String line) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.line = line;
	}

	public Long id() {
		return id;
	}

	public String name() {
		return name;
	}

	public String address() {
		return address;
	}

	public double latitude() {
		return latitude;
	}

	public double longitude() {
		return longitude;
	}

	public String line() {
		return line;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Station station = (Station)o;
		return Double.compare(station.latitude, latitude) == 0
			&& Double.compare(station.longitude, longitude) == 0 && Objects.equals(id, station.id)
			&& Objects.equals(name, station.name) && Objects.equals(address, station.address)
			&& Objects.equals(line, station.line);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, address, latitude, longitude, line);
	}
}
