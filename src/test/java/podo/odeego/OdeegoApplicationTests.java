package podo.odeego;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OdeegoApplicationTests {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Test
	void contextLoads() {
	}

	@Test
	void ciTest() {
		log.info("CI test test");
	}
}
