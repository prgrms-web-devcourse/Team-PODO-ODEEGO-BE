package podo.odeego.domain.path.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.path.dto.PathInfo;
import podo.odeego.domain.path.dto.PathResponse;
import podo.odeego.domain.station.service.StationFindService;

@Service
@Transactional(readOnly = true)
public class PathConvertService {

	public final StationFindService stationFindService;

	public PathConvertService(StationFindService stationFindService) {
		this.stationFindService = stationFindService;
	}

	public PathResponse convert(PathInfo path) {
		return new PathResponse(
			path,
			stationFindService.findAllByNames(path.getStations())
		);
	}
}
