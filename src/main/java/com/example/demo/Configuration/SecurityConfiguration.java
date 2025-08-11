package com.example.demo.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.Security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final AuthenticationProvider auth;
	private final JwtAuthenticationFilter filter;
	private static final String[] SWAGGER_WHITELIST = {
		    "/v3/api-docs/**",
		    "/swagger-ui/**",
		    "/swagger-ui.html",
		    "/swagger-resources/**",
		    "/webjars/**"
		};
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf()
			.disable()
			.authorizeHttpRequests()
			.requestMatchers(SWAGGER_WHITELIST)
			.permitAll()
			.requestMatchers("/api/auth/**")
			.permitAll()
			.requestMatchers("/api/admin/**").hasRole("ADMIN")
			.requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
			.requestMatchers("/api/student/**").hasRole("USER")
			.anyRequest()
			.authenticated()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authenticationProvider(auth)
			.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
			
		return http.build();
	}
}
