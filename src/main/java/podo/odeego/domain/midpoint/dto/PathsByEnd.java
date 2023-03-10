package podo.odeego.domain.midpoint.dto;

import java.util.List;
import java.util.stream.Collectors;

import podo.odeego.domain.path.dto.PathInfo;

public record PathsByEnd(
	String station,
	List<PathInfo> paths
) {
	public static List<PathsByEnd> from(List<PathInfo> paths) {
		return paths.stream()
			.collect(Collectors.groupingBy(PathInfo::endStation))
			.entrySet()
			.stream()
			.map(entry -> new PathsByEnd(entry.getKey(), entry.getValue()))
			.toList();
	}
}
