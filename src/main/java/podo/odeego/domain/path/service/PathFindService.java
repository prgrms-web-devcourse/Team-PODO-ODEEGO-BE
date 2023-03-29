package podo.odeego.domain.path.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.path.dto.PathInfo;
import podo.odeego.domain.path.repository.PathRepository;
import podo.odeego.domain.station.repository.StationRepository;

@Service
@Transactional(readOnly = true)
public class PathFindService {

	private final PathRepository pathRepository;
	private final StationRepository stationRepository;

	public PathFindService(PathRepository pathRepository, StationRepository stationRepository) {
		this.pathRepository = pathRepository;
		this.stationRepository = stationRepository;
	}

	public List<PathInfo> findAllByStarts(List<String> startNames) {
		List<String> endNames = stationRepository.findAllStationNames();
		return pathRepository.findAllByStartStationIn(startNames, endNames);
	}

}
