package podo.odeego.domain.midpoint.dto;

import java.util.List;

import podo.odeego.domain.path.dto.PathInfo;

public record NormalizedPathsToEnd(String end, List<PathInfo> pathInfos, double sortParameter) {
}
