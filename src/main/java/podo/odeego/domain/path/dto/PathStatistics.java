package podo.odeego.domain.path.dto;

import java.util.List;

import podo.odeego.domain.midpoint.dto.PathsByEnd;
import podo.odeego.domain.path.entity.Path;

public record PathStatistics(
	String end,
	List<Path> PathsToEnd,
	Double average,
	Double standardDeviation
) {

	public static PathStatistics from(PathsByEnd paths) {
		double average = getAverage(paths);
		double standardDeviation = getStandardDeviation(paths, average);

		return new PathStatistics(paths.station(), paths.paths(), average, standardDeviation);
	}

	private static double getAverage(PathsByEnd paths) {
		return paths.paths()
			.stream()
			.mapToDouble(Path::requiredTime)
			.summaryStatistics()
			.getAverage();
	}

	private static double getStandardDeviation(PathsByEnd paths, double average) {
		return Math.sqrt(paths.paths()
			.stream()
			.mapToDouble(path -> Math.pow((double)(path.requiredTime()) - average, 2))
			.summaryStatistics()
			.getAverage());
	}
}
