package podo.odeego.domain.path.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import podo.odeego.domain.path.entity.Path;

public interface PathRepository extends Repository<Path, Long> {

	List<Path> findAllByStartStationIn(List<String> stationNames);
}
