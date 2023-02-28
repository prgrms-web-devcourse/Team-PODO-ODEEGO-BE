package podo.odeego.domain.station.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import podo.odeego.domain.station.entity.Station;

public interface StationRepository extends JpaRepository<Station, Long> {

	List<Station> findAllByName(String name);
}
