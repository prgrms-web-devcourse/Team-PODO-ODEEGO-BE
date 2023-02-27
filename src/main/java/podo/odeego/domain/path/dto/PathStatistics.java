package podo.odeego.domain.path.dto;

import java.util.List;

import podo.odeego.domain.midpoint.dto.PathsByEnd;
import podo.odeego.domain.path.entity.Path;

public record PathStatistics(
	String end,
	Double average,
	Double standardDeviation
) {

	private static final Comparator<PathStatistics> DEFAULT_SORT = Comparator.comparing(PathStatistics::average)
		.thenComparing(PathStatistics::standardDeviation)
		.reversed();

	private static PathStatistics from(PathsByEnd paths) {
		double average = getAverage(paths);
		double standardDeviation = getStandardDeviation(paths, average);

		return new PathStatistics(paths.station(), average, standardDeviation);
	}

	// TODO: 구조 바꾸기
	private static List<PathStatistics> analysis(List<Path> paths, Comparator<PathStatistics> sort) {
		return PathsByEnd.from(paths)
			.stream()
			.map(PathStatistics::from)
			.sorted(sort)
			.toList();
	}

	public static List<PathStatistics> analysis(List<Path> paths) {
		return analysis(paths, DEFAULT_SORT);
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
