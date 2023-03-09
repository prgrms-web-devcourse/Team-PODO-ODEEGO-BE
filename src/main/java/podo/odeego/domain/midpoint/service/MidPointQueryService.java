package podo.odeego.domain.midpoint.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.midpoint.dto.MidPointResponse;
import podo.odeego.domain.midpoint.dto.MidPointSearchRequest;
import podo.odeego.domain.midpoint.dto.MidPointSearchResponse;
import podo.odeego.domain.path.entity.Path;
import podo.odeego.domain.path.service.PathFindService;
import podo.odeego.domain.station.dto.StationInfo;
import podo.odeego.domain.station.service.StationFindService;

@Service
@Transactional(readOnly = true)
public class MidPointQueryService {

	private final StationFindService stationFindService;
	private final PathFindService pathFindService;
	private final MidpointEstimateService midpointEstimateService;

	public MidPointQueryService(
		StationFindService stationFindService,
		PathFindService pathFindService,
		MidpointEstimateService midpointEstimateService
	) {
		this.stationFindService = stationFindService;
		this.pathFindService = pathFindService;
		this.midpointEstimateService = midpointEstimateService;
	}

	public MidPointSearchResponse search(MidPointSearchRequest midPointSearchRequest) {

		if (midPointSearchRequest.isAllSameStart()) {
			StationInfo start = stationFindService.findByName(midPointSearchRequest.getFirstStart());
			return MidPointSearchResponse.fromOne(start);
		}

		stationFindService.verifyStationsExists(midPointSearchRequest.getStartNames());

		List<Path> allPathsByStart = pathFindService.findAllByStarts(midPointSearchRequest.getStartNames());

		List<MidPointResponse> midPointResponses = midpointEstimateService.determine(allPathsByStart);

		return MidPointSearchResponse.from(midPointSearchRequest, midPointResponses);
	}
}
