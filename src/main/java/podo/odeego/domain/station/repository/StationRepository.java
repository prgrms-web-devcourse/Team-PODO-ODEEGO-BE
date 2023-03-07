package podo.odeego.domain.station.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import podo.odeego.domain.station.dto.StationNameQueryDto;
import podo.odeego.domain.station.entity.Station;

public interface StationRepository extends JpaRepository<Station, Long> {

	List<Station> findAllByName(String name);

	boolean existsByName(String name);

	@Query("""
		select new podo.odeego.domain.station.dto.StationNameQueryDto(s.name)
		from Station s
		group by s.name""")
	List<StationNameQueryDto> findAllGroupByName();
}
