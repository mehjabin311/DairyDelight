package com.dairyproject.exceptions;

public class ConsumerNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConsumerNotFoundException(String messgae) {
		super(messgae);
	}

}
