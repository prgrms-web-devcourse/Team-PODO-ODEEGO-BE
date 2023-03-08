package podo.odeego.domain.station.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.station.dto.StationNameQueryDto;
import podo.odeego.domain.station.entity.Station;
import podo.odeego.domain.station.exception.StationNotFoundException;
import podo.odeego.domain.station.repository.StationRepository;

@Service
@Transactional(readOnly = true)
public class StationFindService {

	private final StationRepository stationRepository;

	public StationFindService(StationRepository stationRepository) {
		this.stationRepository = stationRepository;
	}

	public Station findByName(String name) {
		return stationRepository.findAllByName(name)
			.stream()
			.findAny()
			.orElseThrow(() -> new StationNotFoundException("Can not found Station by %s".formatted(name)));
	}

	// TODO: 중복된 역이 들어올 때 DB 에서 가져오지 않게 짜기
	public List<Station> findAllByNames(List<String> names) {
		return names.stream()
			.map(this::findByName)
			.toList();
	}

	public void verifyStationExists(String name) {
		if (!stationRepository.existsByName(name)) {
			throw new StationNotFoundException("Cannot find Station for name=%s.".formatted(name));
		}
	}

	public List<StationNameQueryDto> getAllStationName() {
		return stationRepository.findAllGroupByName();
	}
}
