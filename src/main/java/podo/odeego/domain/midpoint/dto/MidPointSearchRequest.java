package podo.odeego.domain.midpoint.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record MidPointSearchRequest(

	@NotNull
	@Size(min = 2, max = 4, message = "출발지의 개수를 확인해주세요.")
	List<Start> stations
) {
	public record Start(

		@NotBlank
		String stationName,

		@NotNull
		@DecimalMin("0.0")
		double lat,

		@NotNull
		@DecimalMin("0.0")
		double lng
	) {
		@Override
		public String stationName() {
			if (stationName.endsWith("호선")) {
				return stationName.split(" ")[0];
			}

			return stationName;
		}
	}

	public boolean isAllSameStart() {
		return stations.stream()
			.collect(Collectors.groupingBy(Start::stationName))
			.size() == 1;
	}

	public List<String> getStartNames() {
		return stations.stream()
			.map(Start::stationName)
			.toList();
	}

	public String getFirstStart() {
		return stations.get(0)
			.stationName();
	}
}
