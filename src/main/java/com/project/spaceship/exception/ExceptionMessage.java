package com.project.spaceship.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ExceptionMessage {

	private final MessageSource messageSource;

	public String getMessageItemNotFound(String entity, Long id) {
		return this.messageSource.getMessage("itemNotFound.message", new Object[] { entity, id }, LocaleContextHolder.getLocale());
	}

	public String getMessageResourceNotFound(String message) {
		return this.messageSource.getMessage("resourceNotFound", new Object[] { message }, LocaleContextHolder.getLocale());
	}

}
