package com.safetynet.alerts.exception;

public class MRNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MRNotFoundException() {

		super("No data found");
	}
}
