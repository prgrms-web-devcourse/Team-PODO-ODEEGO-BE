package podo.odeego.domain.path.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.path.dto.PathResponse;
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
		return path.getPaths()
			.stream()
			.map(stationFindService::findByName)
			.toList();
	}

	public List<Path> findAllByStarts(List<Station> starts) {
		return starts.stream()
			.map(station -> pathRepository.findAllByStart(station.name()))
			.flatMap(Collection::stream)
			.toList();
	}

	public List<PathResponse> findAllPathResponses(List<Path> paths) {
		return paths.stream()
			.map(path -> PathResponse.from(path, findAllStationsInPath(path)))
			.toList();
	}

}
