package podo.odeego.domain.midpoint.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.midpoint.dto.MidPointResponse;
import podo.odeego.domain.midpoint.dto.NormalizedPathsToEnd;
import podo.odeego.domain.path.dto.PathResponse;
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

	private MidPointResponse convert(NormalizedPathsToEnd normalizedPathsToEnd) {
		StationInfo midPointStation = stationFindService.findByName(normalizedPathsToEnd.end());
		List<PathResponse> pathResponses = normalizedPathsToEnd.pathInfos()
			.stream()
			.map(pathConvertService::convert)
			.toList();

		return new MidPointResponse(midPointStation, pathResponses);
	}

	public List<MidPointResponse> convert(List<NormalizedPathsToEnd> resolvedPathStatistics) {
		return resolvedPathStatistics.stream()
			.map(this::convert)
			.toList();
	}
}
