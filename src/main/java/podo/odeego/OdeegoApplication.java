package podo.odeego;

import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import podo.odeego.domain.util.TimeUtils;

@EnableJpaAuditing
@SpringBootApplication
public class OdeegoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OdeegoApplication.class, args);
	}

	@PostConstruct
	public void setApplicationTimeZoneAndLocale() {
		TimeZone.setDefault(TimeUtils.getDefaultTimeZone());
		Locale.setDefault(Locale.KOREA);
	}
}
