package com.safetynet.alerts.exception;

public class PAlreadyCreatedException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PAlreadyCreatedException() {

		super("Data already created.");
	}
}
