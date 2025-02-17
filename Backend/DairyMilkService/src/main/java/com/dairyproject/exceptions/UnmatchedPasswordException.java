package com.dairyproject.exceptions;

public class UnmatchedPasswordException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnmatchedPasswordException(String message) {
		super(message);
	}
}
