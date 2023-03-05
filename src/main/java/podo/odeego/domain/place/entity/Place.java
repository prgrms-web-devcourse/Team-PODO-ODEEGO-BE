package podo.odeego.domain.place.entity;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import podo.odeego.domain.place.exception.PlaceCategoryNotValidException;
import podo.odeego.domain.place.exception.PlaceImageDuplicatedException;
import podo.odeego.domain.type.Address;

@Entity
public class Place {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "place_id")
	private Long id;

	@Column(updatable = false)
	private String name;

	@Embedded
	private Address address;

	private String stationName;

	@Enumerated(value = STRING)
	private PlaceCategory category;

	@OneToMany(mappedBy = "place", cascade = {PERSIST, REMOVE})
	private List<PlaceImage> images = new ArrayList<>();

	protected Place() {
	}

	private Place(String name, Address address, String stationName, PlaceCategory category) {
		this.name = name;
		this.address = address;
		this.stationName = stationName;
		this.category = category;
	}

	public static Place of(String name, Address address, String stationName, PlaceCategory category) {
		if (category.isAll()) {
			throw new PlaceCategoryNotValidException(
				"PlaceCategory '%s' is invalid for create Place object.".formatted(category.toString())
			);
		}
		return new Place(name, address, stationName, category);
	}

	public void addImage(PlaceImage image) {
		if (this.images.contains(image)) {
			throw new PlaceImageDuplicatedException(
				"Place '%d' is already containing '%s'".formatted(this.id, image.toString())
			);
		}
		this.images.add(image);
		image.AssignPlace(this);
	}

	public Long id() {
		return id;
	}

	public String name() {
		return name;
	}

	public Address address() {
		return address;
	}

	public String stationName() {
		return stationName;
	}
}
