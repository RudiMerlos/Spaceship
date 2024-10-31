package com.project.spaceship.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler {

	private final ExceptionMessage exceptionMessage;

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<String> noResourceFoundExceptionHandler(NoResourceFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.exceptionMessage.getMessageResourceNotFound(ex.getMessage()));
	}

	@ExceptionHandler(ItemNotFoundException.class)
	public ResponseEntity<String> itemNotFoundExceptionHandler(ItemNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

}
