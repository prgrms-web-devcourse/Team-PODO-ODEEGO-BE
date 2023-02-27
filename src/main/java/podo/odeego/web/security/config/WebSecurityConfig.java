package podo.odeego.web.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import podo.odeego.domain.member.service.MemberService;
import podo.odeego.web.security.jwt.JwtProvider;
import podo.odeego.web.security.oauth2.OAuth2AuthenticationSuccessHandler;

@EnableWebSecurity
public class WebSecurityConfig {

	private final MemberService memberService;
	private final JwtProvider jwtProvider;
	private final String origin;

	public WebSecurityConfig(
		MemberService memberService,
		JwtProvider jwtProvider,
		@Value("${server.host.front}") String origin) {
		this.memberService = memberService;
		this.jwtProvider = jwtProvider;
		this.origin = origin;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.formLogin().disable()
			.csrf().disable()
			.cors().configurationSource(corsConfigurationSource());

		http
			.oauth2Login()
			.successHandler(oAuth2AuthenticationSuccessHandler());

		return http.build();
	}

	@Bean
	public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
		return new OAuth2AuthenticationSuccessHandler(memberService, jwtProvider);
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.addAllowedOrigin(origin);
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
