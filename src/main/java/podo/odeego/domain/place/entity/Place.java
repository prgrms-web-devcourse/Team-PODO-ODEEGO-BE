package podo.odeego.domain.place.entity;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.BatchSize;

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

	private String shareUrl;

	private String stationName;

	@Enumerated(value = STRING)
	private PlaceCategory category;

	@BatchSize(size = 100)
	@OneToMany(mappedBy = "place", cascade = ALL, orphanRemoval = true)
	private final List<PlaceImage> images = new ArrayList<>();

	protected Place() {
	}

	public Place(String name, Address address, String stationName, PlaceCategory category) {
		this(name, address, null, stationName, category);
	}

	public Place(String name, Address address, String shareUrl, String stationName, PlaceCategory category) {
		this.name = name;
		this.address = address;
		this.shareUrl = shareUrl;
		this.stationName = stationName;
		this.category = category;
	}

	public void addImage(PlaceImage image) {
		if (isImageDuplicated(image)) {
			throw new PlaceImageDuplicatedException(
				"Place '%d' is already containing '%s'".formatted(this.id, image.getUrl())
			);
		}
		this.images.add(image);
		image.AssignPlace(this);
	}

	private boolean isImageDuplicated(PlaceImage image) {
		return this.images.stream()
			.map(PlaceImage::image)
			.anyMatch(existingImage -> existingImage.equals(image.image()));
	}

	public Long id() {
		return id;
	}

	public String name() {
		return name;
	}

	public String address() {
		return address.address();
	}

	public String stationName() {
		return stationName;
	}

	public String shareUrl() {
		return shareUrl;
	}

	public PlaceCategory category() {
		return category;
	}

	public List<PlaceImage> images() {
		return Collections.unmodifiableList(this.images);
	}
}
