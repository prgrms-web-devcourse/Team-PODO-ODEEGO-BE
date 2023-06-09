package podo.odeego.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;

import redis.embedded.RedisServer;

@TestConfiguration
public class TestRedisConfig {

	Logger log = LoggerFactory.getLogger(TestRedisConfig.class);

	private final RedisServer redisServer;

	public TestRedisConfig() {
		this.redisServer = new RedisServer();
	}

	@PostConstruct
	public void redisServer() {
		try {
			log.info("Embedded RedisServer started");
			redisServer.start();
		} catch (RuntimeException e) {
			log.warn("Embedded RedisServer already created");
		}
	}

	@PreDestroy
	public void stopRedis() {
		redisServer.stop();
	}
}
