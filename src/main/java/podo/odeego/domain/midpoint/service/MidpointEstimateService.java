package podo.odeego.domain.midpoint.service;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.midpoint.dto.MidPointResponse;
import podo.odeego.domain.midpoint.dto.NormalizedPathsToEnd;
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
		List<PathStatistics> allPathStatistics = PathsByEnd.from(allPathsByStart)
			.stream()
			.map(PathStatistics::from)
			.toList();

		DoubleSummaryStatistics pathsAverageStatistics = allPathStatistics.stream()
			.mapToDouble(PathStatistics::average)
			.summaryStatistics();

		DoubleSummaryStatistics standardDeviationStatistics = allPathStatistics.stream()
			.mapToDouble(PathStatistics::standardDeviation)
			.summaryStatistics();

		List<NormalizedPathsToEnd> resolvedPathsToEnd = allPathStatistics.stream()
			.map(pathStatistics -> {
				double normalizedAverage =
					(pathStatistics.average() - pathsAverageStatistics.getMin()) / (pathsAverageStatistics.getMax()
						- pathsAverageStatistics.getMin());
				double normalizedStandardDeviation =
					(pathStatistics.standardDeviation() - standardDeviationStatistics.getMin()) / (
						standardDeviationStatistics.getMax() - standardDeviationStatistics.getMin());
				return new NormalizedPathsToEnd(pathStatistics.end(), pathStatistics.pathsToEnd(),
					normalizedAverage + normalizedStandardDeviation);
			})
			.sorted(Comparator.comparing(NormalizedPathsToEnd::sortParameter))
			.limit(DEFAULT_SLICE_NUM)
			.toList();

		return midPointConvertService.convert(resolvedPathsToEnd);
	}
}
