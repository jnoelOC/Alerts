package com.safetynet.alerts.model;

import java.util.List;

public class Firestations {
	private List<Firestation> firestations;

	public Firestations() {
	}

	public Firestations(List<Firestation> firestations) {
		this.firestations = firestations;
	}

	public List<Firestation> getFirestations() {
		return firestations;
	}

	public void setFirestations(List<Firestation> firestations) {
		this.firestations = firestations;
	}

}
