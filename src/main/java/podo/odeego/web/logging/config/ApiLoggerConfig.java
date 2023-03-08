package podo.odeego.web.logging.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import podo.odeego.web.logging.ApiLogInterceptor;

@Configuration
public class ApiLoggerConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ApiLogInterceptor())
			.addPathPatterns("/**/api/**");
	}
}
