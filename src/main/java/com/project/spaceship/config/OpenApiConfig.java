package com.project.spaceship.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI customOpenAPI() {
		// @formatter:off
		return new OpenAPI().info(new Info()
				.title("Spaceships API")
				.version("1.0")
				.description("API for spaceships management."));
		// @formatter:on
	}

}
