package podo.odeego.domain.path.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.path.entity.Path;
import podo.odeego.domain.path.repository.PathRepository;
import podo.odeego.domain.station.entity.Station;
import podo.odeego.domain.station.service.StationFindService;

@Service
@Transactional(readOnly = true)
public class PathFindService {

	private final PathRepository pathRepository;
	private final StationFindService stationFindService;

	public PathFindService(PathRepository pathRepository,
		StationFindService stationFindService) {
		this.pathRepository = pathRepository;
		this.stationFindService = stationFindService;
	}

	private List<Station> findAllStationsInPath(Path path) {
		return path.getStations()
			.stream()
			.map(stationFindService::findByName)
			.toList();
	}

	public List<Path> findAllByStarts(List<Station> starts) {
		return starts.stream()
			.map(station -> pathRepository.findAllByStartStation(station.name()))
			.flatMap(Collection::stream)
			.toList();
	}

}
