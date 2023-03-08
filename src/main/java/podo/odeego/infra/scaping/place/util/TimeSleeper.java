package podo.odeego.infra.scaping.place.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeSleeper {

	public static final int DEFAULT_SLEEP_TIME = 300;
	private static final Logger log = LoggerFactory.getLogger(TimeSleeper.class);

	private TimeSleeper() {
	}

	public static void waitForWhile(int milliSeconds) {
		try {
			Thread.sleep(milliSeconds);
		} catch (InterruptedException e) {
			log.info("InterruptedException occurred while Thread Sleep.");
		}
	}
}
