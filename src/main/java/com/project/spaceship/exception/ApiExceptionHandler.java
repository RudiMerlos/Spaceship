package com.project.spaceship.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<String> noResourceFoundExceptionHandler(NoResourceFoundException ex) {
		return ResponseEntity.badRequest().body("Resource not found: " + ex.getMessage());
	}

	@ExceptionHandler(ItemNotFoundException.class)
	public ResponseEntity<String> itemNotFoundExceptionHandler(ItemNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

}
