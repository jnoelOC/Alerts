package com.safetynet.alerts.model;

import java.util.List;

public class Persons {

	private List<Person> persons;

	public Persons() {
	}

	public Persons(List<Person> persons) {
		this.persons = persons;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

}
