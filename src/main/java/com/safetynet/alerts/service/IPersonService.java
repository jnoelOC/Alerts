package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.model.Person;

public interface IPersonService {

	public void addPerson(PersonDTO personDTO);

	// public void addPerson(Person person);

	// public List<Person> findAllPersons();

	public List<PersonDTO> findAllPersons();

	public Person getOnePerson(String firstName, String lastName);

	public void updateOnePerson(PersonDTO personDTO);

	public void deleteOnePerson(Person person);
}
