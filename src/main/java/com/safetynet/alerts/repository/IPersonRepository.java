package com.safetynet.alerts.repository;

import java.util.List;

import com.safetynet.alerts.model.Person;

public interface IPersonRepository {

	public Person save(Person person);

	public List<Person> findAllPersons();

	public Person readAPerson(String firstname, String lastname);

	public Person updateAPerson(Person person);

	public boolean deleteAPerson(Person person);

	// URL7
	public List<String> getEmailsFrom(String city);
}
