package podo.odeego.domain.place.entity;

import static javax.persistence.FetchType.*;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import podo.odeego.domain.type.Image;

@Entity
public class PlaceImage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "place_image_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "place_id")
	private Place place;

	@Embedded
	private Image image;

	protected PlaceImage() {
	}

	public PlaceImage(Place place, Image image) {
		this.place = place;
		this.image = image;
	}

	public Long id() {
		return id;
	}

	public Place place() {
		return place;
	}

	public Image image() {
		return image;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PlaceImage that = (PlaceImage)o;
		return Objects.equals(id(), that.id()) && Objects.equals(place(), that.place())
			&& Objects.equals(image(), that.image());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id(), place(), image());
	}

	@Override
	public String toString() {
		return "PlaceImage{" +
			"id=" + id +
			", place=" + place +
			", image=" + image.url() +
			'}';
	}

	public void AssignPlace(Place place) {
		this.place = place;
	}
}
