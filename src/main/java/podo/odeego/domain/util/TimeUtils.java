package podo.odeego.domain.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeUtils {

	public static final String ZONE_ASIA_SEOUL = "Asia/Seoul";

	private TimeUtils() {
	}

	public static LocalDateTime getCurrentSeoulTime() {
		return ZonedDateTime.now(ZoneId.of(ZONE_ASIA_SEOUL))
			.toLocalDateTime();
	}

	public static Duration toDuration(LocalTime localTime) {
		return Duration.ofMinutes(localTime.getHour() * 60 + localTime.getMinute());
	}

	public static LocalTime toLocalTime(Duration duration) {
		return LocalTime.of(
			duration.toHoursPart(),
			duration.toMinutesPart(),
			duration.toSecondsPart(),
			duration.toNanosPart()
		);
	}
}
