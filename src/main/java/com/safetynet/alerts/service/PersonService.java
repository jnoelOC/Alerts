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

	@Override
	public List<Person> findAllPersons() {
		return personRepository.findAllPersons();
	}

	@Override
	public void addPerson(Person person) {
		this.personRepository.save(person);
	}

	@Override
	public Person getOnePerson(String firstName, String lastName) {
		return this.personRepository.readAPerson(firstName, lastName);
	}

	@Override
	public void updateOnePerson(Person person) {
		personRepository.updateAPerson(person);
	}

	@Override
	public void deleteOnePerson(Person person) {
		personRepository.deleteAPerson(person);
	}
}
