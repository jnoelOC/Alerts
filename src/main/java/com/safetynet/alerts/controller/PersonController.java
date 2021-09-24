package com.safetynet.alerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.IPersonService;

@RestController
public class PersonController {

	@Autowired
	IPersonService personService;

	@GetMapping("/persons")
	public List<PersonDTO> findAllPersons() {

		return personService.findAllPersons();
	}

	@GetMapping("/person/{firstName}/{lastName}")
	public ResponseEntity<PersonDTO> findOnePerson(@PathVariable String firstName, @PathVariable String lastName) {

		PersonDTO personDTO = personService.getOnePerson(firstName, lastName);
		if (personDTO == null) {
			return new ResponseEntity<>(personDTO, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(personDTO, HttpStatus.FOUND);
		}

	}

	@PutMapping("/person/update")
	public ResponseEntity<PersonDTO> updatePerson(@RequestBody PersonDTO personDTO) {

		Person person = personService.updateOnePerson(personDTO);
		if (person == null) {
			return new ResponseEntity<>(personDTO, HttpStatus.NOT_MODIFIED);
		} else {
			return new ResponseEntity<>(personDTO, HttpStatus.OK);
		}
	}

	@DeleteMapping("/person/delete/{firstName}/{lastName}")
	public ResponseEntity<Void> deletePerson(@PathVariable String firstName, @PathVariable String lastName) {

		boolean isRemoved = false;
		try {
			PersonDTO personDTO = personService.getOnePerson(firstName, lastName);
			isRemoved = personService.deleteOnePerson(personDTO);
		} catch (NullPointerException e) {
			// logger
		} catch (Exception ex) {
			// logger
		}

		if (isRemoved) {
			return new ResponseEntity<>(HttpStatus.GONE);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("/person/create")
	public ResponseEntity<PersonDTO> createPersonWithBodyParam(@RequestBody PersonDTO personDTO) {

		Person person = personService.addPerson(personDTO);
		if (person == null) {
			return new ResponseEntity<>(personDTO, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(personDTO, HttpStatus.CREATED);
		}

	}

}
