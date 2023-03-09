package podo.odeego.domain.midpoint.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.midpoint.dto.MidPointResponse;
import podo.odeego.domain.midpoint.dto.PathsByEnd;
import podo.odeego.domain.path.dto.PathInfo;
import podo.odeego.domain.path.dto.PathStatistics;

@Service
@Transactional(readOnly = true)
public class MidpointEstimateService {

	public static final int DEFAULT_SLICE_NUM = 3;

	public final MidPointConvertService midPointConvertService;

	public MidpointEstimateService(MidPointConvertService midPointConvertService) {
		this.midPointConvertService = midPointConvertService;
	}

	public List<MidPointResponse> determine(List<PathInfo> allPathsByStart) {
		List<PathStatistics> resolvedPathStatistics = PathsByEnd.from(allPathsByStart)
			.stream()
			.map(PathStatistics::from)
			.sorted(
				Comparator.comparing(PathStatistics::average)
					.thenComparing(PathStatistics::standardDeviation)
			)
			.limit(DEFAULT_SLICE_NUM)
			.toList();

		return midPointConvertService.convert(resolvedPathStatistics);
	}
}
