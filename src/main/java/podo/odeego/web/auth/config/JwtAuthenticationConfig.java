package podo.odeego.web.auth.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import podo.odeego.web.auth.JwtAuthenticationInterceptor;
import podo.odeego.web.auth.JwtProvider;
import podo.odeego.web.auth.LoginMemberArgumentResolver;

@Configuration
public class JwtAuthenticationConfig implements WebMvcConfigurer {

	private final JwtProvider jwtProvider;

	public JwtAuthenticationConfig(JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtAuthenticationInterceptor())
			.addPathPatterns("/**/api/v1/members/**", "/**/api/v1/groups/**");
	}

	@Bean
	public JwtAuthenticationInterceptor jwtAuthenticationInterceptor() {
		return new JwtAuthenticationInterceptor(jwtProvider);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new LoginMemberArgumentResolver());
	}
}
