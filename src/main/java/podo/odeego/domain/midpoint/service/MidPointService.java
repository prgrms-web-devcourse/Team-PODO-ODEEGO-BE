package podo.odeego.domain.midpoint.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.midpoint.dto.MidPointResponse;
import podo.odeego.domain.midpoint.dto.MidPointSearchRequest;
import podo.odeego.domain.midpoint.dto.MidPointSearchResponse;
import podo.odeego.domain.midpoint.vo.PathStatistics;
import podo.odeego.domain.path.dto.PathResponse;
import podo.odeego.domain.path.entity.Path;
import podo.odeego.domain.path.service.PathFindService;
import podo.odeego.domain.station.entity.Station;
import podo.odeego.domain.station.service.StationFindService;

@Service
@Transactional(readOnly = true)
public class MidPointService {

	public static final int DEFAULT_SLICE_NUM = 3;
	private final StationFindService stationFindService;
	private final PathFindService pathFindService;

	public MidPointService(StationFindService stationFindService,
		PathFindService pathFindService) {
		this.stationFindService = stationFindService;
		this.pathFindService = pathFindService;
	}

	public MidPointSearchResponse search(MidPointSearchRequest midPointSearchRequest) {

		if (midPointSearchRequest.isAllSameStart()) {
			Station start = stationFindService.findByName(midPointSearchRequest.getFirstStart());
			return MidPointSearchResponse.fromOne(start);
		}

		List<Station> starts = stationFindService.findAllByNames(midPointSearchRequest.getStartNames());

		List<Path> allPathsByStart = pathFindService.findAllByStarts(starts);

		List<Station> resolvedStations = resolve(allPathsByStart);

		List<PathResponse> allPathResponses = pathFindService.findAllPathResponses(allPathsByStart);

		List<MidPointResponse> midPointResponses = convert(allPathResponses, resolvedStations);

		return MidPointSearchResponse.from(starts, midPointResponses);
	}

	private List<Station> resolve(List<Path> allPathsByStart) {

		List<PathStatistics> pathStatistics = PathStatistics.analysis(allPathsByStart)
			.subList(0, DEFAULT_SLICE_NUM);

		List<String> endStationNames = PathStatistics.toEndStationNames(pathStatistics);

		return stationFindService.findAllByNames(endStationNames);
	}

	private List<MidPointResponse> convert(List<PathResponse> allPathResponses, List<Station> resolvedStations) {
		return resolvedStations.stream()
			.map(station -> new MidPointResponse(station, allPathResponses))
			.toList();
	}
}
