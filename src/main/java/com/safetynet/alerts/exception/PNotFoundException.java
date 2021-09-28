package com.safetynet.alerts.exception;

public class PNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PNotFoundException() {

		super("No data found.");
	}
}