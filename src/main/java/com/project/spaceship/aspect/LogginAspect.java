package com.project.spaceship.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LogginAspect {

	@Before("execution(* com.project.spaceship.service.SpaceshipQueryService.findById(Long)) && args(id)")
	public void logNegativeId(Long id) {
		if (id < 0) {
			LOGGER.warn("Negative ID: " + id);
		}
	}

}
