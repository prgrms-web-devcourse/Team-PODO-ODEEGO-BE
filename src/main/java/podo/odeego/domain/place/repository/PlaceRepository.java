package podo.odeego.domain.place.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import podo.odeego.domain.place.entity.Place;
import podo.odeego.domain.place.entity.PlaceCategory;

public interface PlaceRepository extends JpaRepository<Place, Long> {

	Page<Place> findPlacesByStationName(String stationName, Pageable pageable);

	Page<Place> findPlacesByStationNameAndCategory(String stationName, PlaceCategory category, Pageable pageable);
}
