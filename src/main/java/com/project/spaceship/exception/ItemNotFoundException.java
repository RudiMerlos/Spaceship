package com.project.spaceship.exception;

public class ItemNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3698400766764215540L;

	public ItemNotFoundException(String message) {
		super("Item not found in database: " + message);
	}

}
