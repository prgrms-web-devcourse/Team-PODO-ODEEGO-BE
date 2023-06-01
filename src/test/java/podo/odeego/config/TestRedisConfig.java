package podo.odeego.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.boot.test.context.TestConfiguration;

import redis.embedded.RedisServer;

@TestConfiguration
public class TestRedisConfig {

	private final RedisServer redisServer;

	public TestRedisConfig() {
		this.redisServer = new RedisServer();
	}

	@PostConstruct
	public void redisServer() {
		redisServer.start();
	}

	@PreDestroy
	public void stopRedis() {
		redisServer.stop();
	}
}
