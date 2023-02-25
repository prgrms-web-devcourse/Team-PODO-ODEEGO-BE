package podo.odeego.domain.station.service;

import java.util.List;

import org.springframework.stereotype.Service;

import podo.odeego.domain.station.entity.Station;
import podo.odeego.domain.station.repository.StationRepository;

@Service
public class StationFindService {

	private final StationRepository stationRepository;

	public StationFindService(StationRepository stationRepository) {
		this.stationRepository = stationRepository;
	}

	public Station findByName(String name) {
		return stationRepository.findByName(name)
			.orElseThrow();
	}

	// TODO: 중복된 역이 들어올 때 DB 에서 가져오지 않게 짜기
	public List<Station> findAllByNames(List<String> names) {
		return names.stream()
			.map(this::findByName)
			.toList();
	}
}
