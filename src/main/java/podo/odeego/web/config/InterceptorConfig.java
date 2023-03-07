package podo.odeego.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import podo.odeego.web.interceptor.JwtAuthenticationInterceptor;
import podo.odeego.web.logging.ApiLogInterceptor;
import podo.odeego.web.security.jwt.JwtProvider;

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
			.addPathPatterns("/**/api/v2/members/my-page"); //TODO: 인증이 필요한 url 매핑해야함
	}

	@Bean
	public JwtAuthenticationInterceptor jwtAuthenticationInterceptor() {
		return new JwtAuthenticationInterceptor(jwtProvider);
	}
}
