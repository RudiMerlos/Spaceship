package com.project.spaceship.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// @formatter:off
		http.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(authorizeRequests -> authorizeRequests
					.requestMatchers(HttpMethod.GET, "/api/spaceships/**").hasAnyRole("USER", "ADMIN")
					.requestMatchers(HttpMethod.POST, "/api/spaceships/**").hasRole("ADMIN")
					.requestMatchers(HttpMethod.PUT, "/api/spaceships/**").hasRole("ADMIN")
					.requestMatchers(HttpMethod.DELETE, "/api/spaceships/**").hasRole("ADMIN")
					.anyRequest().authenticated())
			.httpBasic(withDefaults());
		// @formatter:on
		return http.build();
	}

	@Bean
	UserDetailsService userDetailService() {
		UserDetails admin = User.withUsername("admin").password("{noop}admin").roles("ADMIN").build();
		UserDetails user = User.withUsername("user").password("{noop}user").roles("USER").build();
		return new InMemoryUserDetailsManager(admin, user);
	}

}
