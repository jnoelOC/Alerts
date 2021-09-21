package com.safetynet.alerts.utils;

import org.springframework.stereotype.Component;

import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.model.Person;

@Component
public class PersonMapper {

	public Person toPerson(PersonDTO personDTO) {
		return new Person(personDTO.getFirstName(), personDTO.getLastName(), personDTO.getAddress(),
				personDTO.getCity(), personDTO.getZip(), personDTO.getPhone(), personDTO.getEmail());
	}

	public PersonDTO toPersonDTO(Person person) {
		return new PersonDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getCity(),
				person.getZip(), person.getPhone(), person.getEmail());
	}
}
