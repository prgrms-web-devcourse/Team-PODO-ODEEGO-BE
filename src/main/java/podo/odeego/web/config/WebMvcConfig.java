package podo.odeego.web.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import podo.odeego.web.LoginMemberArgumentResolver;
import podo.odeego.web.api.place.converter.PlaceCategoryConverter;

@Configuration
@EnableWebMvc
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
			.allowedOrigins(localOrigin, releaseOrigin, "http://localhost:3001")
			.allowedMethods("*")
			.allowedHeaders("*");
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new PlaceCategoryConverter());
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new LoginMemberArgumentResolver());
	}
}
