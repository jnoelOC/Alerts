package com.safetynet.alerts.exception;

public class MRAlreadyCreatedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MRAlreadyCreatedException() {

		super("Data already created.");
	}
}
