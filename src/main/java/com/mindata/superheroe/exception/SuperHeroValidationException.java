package com.mindata.superheroe.exception;

public class SuperHeroValidationException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SuperHeroValidationException(String message) {
        super(message);
    }
}