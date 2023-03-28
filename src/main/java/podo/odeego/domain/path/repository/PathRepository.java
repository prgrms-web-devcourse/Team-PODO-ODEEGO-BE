package podo.odeego.domain.path.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import podo.odeego.domain.path.dto.PathInfo;
import podo.odeego.domain.path.entity.Path;

public interface PathRepository extends Repository<Path, Long> {

	@Query("""
			select new podo.odeego.domain.path.dto.PathInfo(p.startStation, p.endStation, p.requiredTime, p.stations)
			from Path p
			where p.startStation in :startNames and p.endStation in :endNames
		""")
	List<PathInfo> findAllByStartStationIn(

		@Param("startNames")
		List<String> startNames,

		@Param("endNames")
		List<String> endNames
	);
}
