package com.safetynet.alerts.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;

@Repository
public class PersonRepository implements IPersonRepository {

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

					persons.get(numPerson).setAddress("1101 new street");
					persons.get(numPerson).setZip("H2Z 2HQ");
					persons.get(numPerson).setCity("Montreal");
					persons.get(numPerson).setPhone("0123456789");
					persons.get(numPerson).setEmail("newstreet@gmail.com");
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
	public Person deleteAPerson(Person person) {
		Iterator<Person> iteratorPersons = persons.iterator();

		try {
			Integer numPerson = 0;
			while (iteratorPersons.hasNext()) {

				if (persons.get(numPerson).getFirstName().equalsIgnoreCase(person.getFirstName())
						&& persons.get(numPerson).getLastName().equalsIgnoreCase(person.getLastName())) {

					persons.remove(persons.get(numPerson));
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

}
