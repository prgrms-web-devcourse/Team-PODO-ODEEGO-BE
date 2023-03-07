package podo.odeego.domain.place.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import podo.odeego.domain.place.entity.Place;
import podo.odeego.domain.place.entity.PlaceCategory;

public interface PlaceRepository extends JpaRepository<Place, Long> {

	List<Place> findPlacesByStationName(String stationName);

	List<Place> findPlacesByStationNameAndCategory(String stationName, PlaceCategory category);
}
