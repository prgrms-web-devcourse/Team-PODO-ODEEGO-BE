package podo.odeego.domain.station.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import podo.odeego.domain.station.entity.Station;

public interface StationRepository extends Repository<Station, Long> {

	List<Station> findAllByName(String name);
}
