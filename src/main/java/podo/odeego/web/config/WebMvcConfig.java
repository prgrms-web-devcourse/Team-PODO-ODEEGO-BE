package podo.odeego.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import podo.odeego.web.api.place.converter.PlaceCategoryConverter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final String localOrigin;
	private final String releaseOrigin;

	public WebMvcConfig(
		@Value("${server.host.front.local}") String localOrigin,
		@Value("${server.host.front.release}") String releaseOrigin
	) {
		this.localOrigin = localOrigin;
		this.releaseOrigin = releaseOrigin;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins(localOrigin, releaseOrigin)
			.allowedMethods(HttpMethod.GET.name())
			.allowedMethods(HttpMethod.POST.name())
			.allowedMethods(HttpMethod.HEAD.name())
			.allowedMethods(HttpMethod.PUT.name())
			.allowedMethods(HttpMethod.DELETE.name())
			.allowedMethods(HttpMethod.TRACE.name())
			.allowedMethods(HttpMethod.PATCH.name())
			.allowedMethods(HttpMethod.OPTIONS.name());
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new PlaceCategoryConverter());
	}
}
