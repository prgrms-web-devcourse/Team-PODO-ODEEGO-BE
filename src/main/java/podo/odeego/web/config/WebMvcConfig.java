package podo.odeego.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

	//TODO: security 패키지를 auth 패키지로 변경 & AuthApi.getMemberInfo() 메서드에서 로그찍는거중에 memberId -> providerId로 수정
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins(localOrigin, releaseOrigin)
			.allowedMethods("*")
			.allowedHeaders("*");
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new PlaceCategoryConverter());
	}
}
