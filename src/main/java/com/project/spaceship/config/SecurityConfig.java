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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
					.requestMatchers(HttpMethod.GET, "/api/spaceships/**").hasAnyRole(UserRole.USER.name(), UserRole.ADMIN.name())
					.requestMatchers(HttpMethod.POST, "/api/spaceships/**").hasRole(UserRole.ADMIN.name())
					.requestMatchers(HttpMethod.PUT, "/api/spaceships/**").hasRole(UserRole.ADMIN.name())
					.requestMatchers(HttpMethod.DELETE, "/api/spaceships/**").hasRole(UserRole.ADMIN.name())
					.anyRequest().authenticated())
			.httpBasic(withDefaults());
		// @formatter:on
		return http.build();
	}

	@Bean
	UserDetailsService userDetailService() {
		// @formatter:off
		UserDetails admin = User.withUsername("admin")
				.password(this.passwordEncoder().encode("admin")).roles(UserRole.ADMIN.name())
				.build();
		UserDetails user = User.withUsername("user")
				.password(this.passwordEncoder().encode("user")).roles(UserRole.USER.name())
				.build();
		// @formatter:on
		return new InMemoryUserDetailsManager(admin, user);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
