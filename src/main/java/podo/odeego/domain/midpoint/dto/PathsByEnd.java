package podo.odeego.domain.midpoint.dto;

import java.util.List;
import java.util.stream.Collectors;

import podo.odeego.domain.path.entity.Path;

public record PathsByEnd(
	String station,
	List<Path> paths
) {
	public static List<PathsByEnd> from(List<Path> paths) {
		return paths.stream()
			.collect(Collectors.groupingBy(Path::endStation))
			.entrySet()
			.stream()
			.map(entry -> new PathsByEnd(entry.getKey(), entry.getValue()))
			.toList();
	}
}
