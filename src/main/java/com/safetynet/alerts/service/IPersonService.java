package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.model.Person;

public interface IPersonService {

	public Person addPerson(PersonDTO personDTO);

	// public void addPerson(Person person);

	// public List<Person> findAllPersons();

	public List<PersonDTO> findAllPersons();

	public PersonDTO getOnePerson(String firstName, String lastName);

	public Person updateOnePerson(PersonDTO personDTO);

	public boolean deleteOnePerson(PersonDTO personDTO);
}
