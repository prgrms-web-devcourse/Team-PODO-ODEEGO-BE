package podo.odeego.domain.path.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.path.dto.PathInfo;
import podo.odeego.domain.path.repository.PathRepository;
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

	public List<PathInfo> findAllByStarts(List<String> startNames) {
		return pathRepository.findAllByStartStationIn(startNames);
	}

}
