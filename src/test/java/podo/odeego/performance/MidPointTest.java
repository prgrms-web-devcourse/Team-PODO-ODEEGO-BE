package podo.odeego.performance;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import podo.odeego.domain.midpoint.dto.MidPointSearchRequest;
import podo.odeego.domain.station.entity.Station;
import podo.odeego.domain.station.repository.StationRepository;
import podo.odeego.web.api.midpoint.MidPointApi;

@SpringBootTest
public class MidPointTest {

	@Autowired
	private MidPointApi midPointApi;

	@Autowired
	private StationRepository stationRepository;

	@Test
	void perform() {

		int count = 0;
		long sum = 0;

		while (count != 1000) {
			long stationId1 = (long)(Math.random() * 768) + 1;
			long stationId2 = (long)(Math.random() * 768) + 1;
			long stationId3 = (long)(Math.random() * 768) + 1;

			if (stationId1 == stationId2 && stationId1 == stationId3) {
				continue;
			}

			Station station1 = stationRepository.findById(stationId1).get();
			Station station2 = stationRepository.findById(stationId2).get();
			Station station3 = stationRepository.findById(stationId3).get();

			MidPointSearchRequest.Start start1 = new MidPointSearchRequest.Start(station1.name(), station1.latitude(),
				station1.longitude());
			MidPointSearchRequest.Start start2 = new MidPointSearchRequest.Start(station2.name(), station2.latitude(),
				station2.longitude());
			MidPointSearchRequest.Start start3 = new MidPointSearchRequest.Start(station3.name(), station3.latitude(),
				station3.longitude());
			MidPointSearchRequest midPointSearchRequest = new MidPointSearchRequest(List.of(start1, start2, start3));

			long before = System.currentTimeMillis();
			midPointApi.search(midPointSearchRequest);
			long after = System.currentTimeMillis();
			long diff = (after - before);
			System.out.println("시간 차이(m) = " + diff);
			sum += diff;
			count++;
		}
		long avg = sum / 1000L;
		System.out.println("result = " + avg);
	}
}
