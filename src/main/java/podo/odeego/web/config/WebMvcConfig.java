package podo.odeego.web.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import podo.odeego.web.api.place.converter.PlaceCategoryConverter;
import podo.odeego.web.auth.JwtAuthenticationInterceptor;
import podo.odeego.web.auth.JwtProvider;
import podo.odeego.web.auth.LoginMemberArgumentResolver;
import podo.odeego.web.logging.ApiLogInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final String localOrigin;
	private final String releaseOrigin;
	private final JwtProvider jwtProvider;

	public WebMvcConfig(
		@Value("${server.host.front.local}") String localOrigin,
		@Value("${server.host.front.release}") String releaseOrigin,
		JwtProvider jwtProvider
	) {
		this.localOrigin = localOrigin;
		this.releaseOrigin = releaseOrigin;
		this.jwtProvider = jwtProvider;
	}

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

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ApiLogInterceptor())
			.order(1)
			.addPathPatterns("/**/api/**");

		registry.addInterceptor(jwtAuthenticationInterceptor())
			.order(2)
			.addPathPatterns("/**/api/v1/members/**", "/**/api/v1/groups/**");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new LoginMemberArgumentResolver());
	}

	@Bean
	public JwtAuthenticationInterceptor jwtAuthenticationInterceptor() {
		return new JwtAuthenticationInterceptor(jwtProvider);
	}
}
