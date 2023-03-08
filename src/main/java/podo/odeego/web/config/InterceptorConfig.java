package podo.odeego.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import podo.odeego.web.interceptor.JwtAuthenticationInterceptor;
import podo.odeego.web.logging.ApiLogInterceptor;
import podo.odeego.web.auth.jwt.JwtProvider;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	private final JwtProvider jwtProvider;

	public InterceptorConfig(JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ApiLogInterceptor())
			.addPathPatterns("/**/api/**");
		registry.addInterceptor(jwtAuthenticationInterceptor())
			.addPathPatterns("/**/api/v1/members/**", "/**/api/v1/groups/**");
	}

	@Bean
	public JwtAuthenticationInterceptor jwtAuthenticationInterceptor() {
		return new JwtAuthenticationInterceptor(jwtProvider);
	}
}
