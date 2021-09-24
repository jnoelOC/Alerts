package com.safetynet.alerts.utils;

import org.springframework.stereotype.Component;

import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.model.Person;

@Component
public class PersonMapper {

	public Person toPerson(PersonDTO personDTO) {

		Person person = null;
		try {
			person = new Person(personDTO.getFirstName(), personDTO.getLastName(), personDTO.getAddress(),
					personDTO.getCity(), personDTO.getZip(), personDTO.getPhone(), personDTO.getEmail());
		} catch (NullPointerException e) {
			// logger.error("Error null pointer : ", e);
		} catch (Exception ex) {
			// logger.error("Error general purpose : ", ex);
		}

		return person;

	}

	public PersonDTO toPersonDTO(Person person) {
		PersonDTO personDTO = null;
		try {
			personDTO = new PersonDTO(person.getFirstName(), person.getLastName(), person.getAddress(),
					person.getCity(), person.getZip(), person.getPhone(), person.getEmail());
		} catch (NullPointerException e) {
			// logger.error("Error null pointer : ", e);
		} catch (Exception ex) {
			// logger.error("Error general purpose : ", ex);
		}

		return personDTO;
	}
}
