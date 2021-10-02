package com.safetynet.alerts.exception;

public class FAlreadyCreatedException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FAlreadyCreatedException() {

		super("Data already created.");
	}
}
