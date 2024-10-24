package com.project.spaceship.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogginAspect {
	
	private static final Logger log = LoggerFactory.getLogger(LogginAspect.class);
	
	@Before("execution(* com.project.spaceship.service.SpaceshipService.findById(Long)) && args(id)")
	public void logNegativeId(Long id) {
		if (id < 0) {
			log.warn("Negative ID: " + id);
		}
	}

}
