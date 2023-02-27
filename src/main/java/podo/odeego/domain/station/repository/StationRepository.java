package podo.odeego.domain.station.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import podo.odeego.domain.station.entity.Station;

public interface StationRepository extends Repository<Station, Long> {

	Optional<Station> findByName(String name);
}
