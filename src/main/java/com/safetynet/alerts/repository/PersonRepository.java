package com.safetynet.alerts.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;

@Repository
public class PersonRepository implements IPersonRepository {

	public static final Logger logger = LogManager.getLogger(PersonRepository.class);

	private List<Person> persons = new ArrayList<>();

	public Person save(Person person) {
		boolean pFound = false;

		if (person == null) {
			pFound = true;
		} else {
			for (Person pers : persons) {
				if (pers.getFirstName().equalsIgnoreCase(person.getFirstName())
						&& pers.getLastName().equalsIgnoreCase(person.getLastName())) {

					pFound = true;
					break;
				}
			}
		}
		if (!pFound) {
			persons.add(person);
			return person;
		} else { // ALREADY CREATED OR NULL
			return null;
		}
	}

	public List<Person> findAllPersons() {
		return persons;
	}

	public Person readAPerson(String firstname, String lastname) {

		for (Person pers : persons) {
			if (pers.getFirstName().equalsIgnoreCase(firstname) && pers.getLastName().equalsIgnoreCase(lastname)) {

				return pers;
			}
		}

		return null;
	}

	public Person updateAPerson(Person person) {

		for (Person pers : persons) {
			if (pers.getFirstName().equalsIgnoreCase(person.getFirstName())
					&& pers.getLastName().equalsIgnoreCase(person.getLastName())) {
				pers.setAddress(person.getAddress());
				pers.setZip(person.getZip());
				pers.setCity(person.getCity());
				pers.setPhone(person.getPhone());
				pers.setEmail(person.getEmail());

				return pers;
			}
		}

		return null;
	}

	public boolean deleteAPerson(Person person) {
		boolean isRemoved = false;
		for (Person pers : persons) {
			if (pers.getFirstName().equalsIgnoreCase(person.getFirstName())
					&& pers.getLastName().equalsIgnoreCase(person.getLastName())) {

				isRemoved = persons.remove(pers);
				break;
			}
		}
		return isRemoved;
	}

	// URL7
	public List<String> getEmailsFrom(String city) {
		List<String> ls = new ArrayList<>();

		if (city == null) {
			return Collections.emptyList();
		} else {
			for (Person person : persons) {
				if (person.getCity().equalsIgnoreCase(city)) {

					ls.add(person.getEmail());
				}
			}
			return ls;
		}

	}
}
