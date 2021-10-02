package com.safetynet.alerts.exception;

public class FNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FNotFoundException() {

		super("No data found.");
	}
}
