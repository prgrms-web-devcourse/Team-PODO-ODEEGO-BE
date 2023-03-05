package podo.odeego.domain.place.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import podo.odeego.domain.place.entity.PlaceImage;

public interface PlaceImageRepository extends JpaRepository<PlaceImage, Long> {
}
