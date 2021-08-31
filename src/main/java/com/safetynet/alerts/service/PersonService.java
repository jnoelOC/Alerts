package com.safetynet.alerts.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository personRepository;

	public List<Person> findAllPersons() {

		return personRepository.getAllPersons();
	}

	public Person getOnePerson(String firtsname, String lastname) {

		return personRepository.getInfoAboutOnePerson(firtsname, lastname);
	}

	public void updateOnePerson(String firtsname, String lastname) throws IOException {
		personRepository.updateInfoAboutOnePerson(firtsname, lastname);
	}
}
