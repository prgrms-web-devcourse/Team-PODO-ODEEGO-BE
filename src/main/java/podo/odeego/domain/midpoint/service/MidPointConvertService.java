package podo.odeego.domain.midpoint.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.midpoint.dto.MidPointResponse;
import podo.odeego.domain.path.dto.PathResponse;
import podo.odeego.domain.path.dto.PathStatistics;
import podo.odeego.domain.path.service.PathConvertService;
import podo.odeego.domain.station.dto.StationInfo;
import podo.odeego.domain.station.service.StationFindService;

@Service
@Transactional(readOnly = true)
public class MidPointConvertService {

	public final StationFindService stationFindService;
	public final PathConvertService pathConvertService;

	public MidPointConvertService(StationFindService stationFindService, PathConvertService pathMapService) {
		this.stationFindService = stationFindService;
		this.pathConvertService = pathMapService;
	}

	private MidPointResponse convert(PathStatistics pathStatistics) {
		StationInfo midPointStation = stationFindService.findByName(pathStatistics.end());
		List<PathResponse> pathResponses = pathStatistics.pathsToEnd()
			.stream()
			.map(pathConvertService::convert)
			.toList();

		return new MidPointResponse(midPointStation, pathResponses);
	}

	public List<MidPointResponse> convert(List<PathStatistics> resolvedPathStatistics) {
		return resolvedPathStatistics.stream()
			.map(this::convert)
			.toList();
	}
}
