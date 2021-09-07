package com.safetynet.alerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.IPersonRepository;

@Service
public class PersonService implements IPersonService {

	@Autowired
	IPersonRepository personRepository;

	public List<Person> findAllPersons() {
		return personRepository.findAllPersons();

	}

	@Override
	public Person addPerson(Person person) {
		return this.personRepository.save(person);
	}

	@Override
	public Person getOnePerson(String firstName, String lastName) {
		return this.personRepository.readAPerson(firstName, lastName);
	}

	public Person updateOnePerson(Person person) {
		return personRepository.updateAPerson(person);
	}

	public Person deleteOnePerson(Person person) {
		return personRepository.deleteAPerson(person);
	}
}
