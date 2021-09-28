package com.safetynet.alerts.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;

@Repository
public class PersonRepository implements IPersonRepository {

	public static final Logger logger = LogManager.getLogger(PersonRepository.class);

	private List<Person> persons = new ArrayList<Person>();

	@Override
	public Person save(Person person) {
		persons.add(person);
		return person;
	}

	@Override
	public List<Person> findAllPersons() {
		return persons;
	}

	@Override
	public Person readAPerson(String firstname, String lastname) {

		Iterator<Person> iteratorPersons = persons.iterator();

		try {
			Integer numPerson = 0;
			while (iteratorPersons.hasNext()) {

				if (persons.get(numPerson).getFirstName().equalsIgnoreCase(firstname)
						&& persons.get(numPerson).getLastName().equalsIgnoreCase(lastname)) {

					return persons.get(numPerson);
				}

				numPerson++;
				iteratorPersons.next();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public Person updateAPerson(Person person) {
		Iterator<Person> iteratorPersons = persons.iterator();

		try {
			Integer numPerson = 0;
			while (iteratorPersons.hasNext()) {

				if (persons.get(numPerson).getFirstName().equalsIgnoreCase(person.getFirstName())
						&& persons.get(numPerson).getLastName().equalsIgnoreCase(person.getLastName())) {

					persons.get(numPerson).setAddress(person.getAddress());
					persons.get(numPerson).setZip(person.getZip());
					persons.get(numPerson).setCity(person.getCity());
					persons.get(numPerson).setPhone(person.getPhone());
					persons.get(numPerson).setEmail(person.getEmail());
					return persons.get(numPerson);
				}

				numPerson++;
				iteratorPersons.next();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteAPerson(Person person) {
		Iterator<Person> iteratorPersons = persons.iterator();
		boolean isRemoved = false;

		try {
			Integer numPerson = 0;
			while (iteratorPersons.hasNext()) {

				if (persons.get(numPerson).getFirstName().equalsIgnoreCase(person.getFirstName())
						&& persons.get(numPerson).getLastName().equalsIgnoreCase(person.getLastName())) {

					persons.remove(persons.get(numPerson));
					isRemoved = true;
					break;
				} else {

					numPerson++;
					iteratorPersons.next();
				}
			}
		} catch (Exception ex) {

		}

		return isRemoved;
	}

}
