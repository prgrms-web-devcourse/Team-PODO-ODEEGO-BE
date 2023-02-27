package podo.odeego.domain.station.entity;

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

}
