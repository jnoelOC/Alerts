package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.model.Person;

public interface IPersonService {
	public Person addPerson(Person person);

	public List<Person> findAllPersons();

	public Person getOnePerson(String firstName, String lastName);

	public Person updateOnePerson(Person person);

	public Person deleteOnePerson(Person person);
}
