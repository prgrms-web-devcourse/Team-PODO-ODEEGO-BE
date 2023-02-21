package podo.odeego.web.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import podo.odeego.domain.user.service.UserService;
import podo.odeego.web.security.oauth2.OAuth2AuthenticationSuccessHandler;

@EnableWebSecurity
public class WebSecurityConfig {

	private final UserService userService;

	public WebSecurityConfig(UserService userService) {
		this.userService = userService;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.oauth2Login()
			.successHandler(oAuth2AuthenticationSuccessHandler());

		return http.build();
	}

	@Bean
	public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
		return new OAuth2AuthenticationSuccessHandler(userService);
	}
}
