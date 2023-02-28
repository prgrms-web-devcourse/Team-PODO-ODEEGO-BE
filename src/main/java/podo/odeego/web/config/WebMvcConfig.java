package podo.odeego.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import podo.odeego.web.api.place.converter.PlaceCategoryConverter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final String origin;

	public WebMvcConfig(@Value("${server.host.front}") String origin) {
		this.origin = origin;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins(origin)
			.allowedMethods("*");
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new PlaceCategoryConverter());
	}
}
