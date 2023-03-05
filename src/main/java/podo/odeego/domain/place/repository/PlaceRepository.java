package podo.odeego.domain.place.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import podo.odeego.domain.place.entity.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
