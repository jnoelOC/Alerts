package com.safetynet.alerts.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;

@RestController
public class PersonController {

	@Autowired
	PersonService personService;

	@GetMapping("/persons")
	public List<Person> findAllPersons() {

		return personService.findAllPersons();
	}

	@GetMapping("/person/{firstName}/{lastName}")
	public Person findOnePerson(@PathVariable String firstName, @PathVariable String lastName) {

		return personService.getOnePerson(firstName, lastName);

	}

//	@PostMapping("/person/create/{firstName}/{lastName}") {}

//	@PatchMapping("/person/patch/{firstName}/{lastName}") {}

	@PutMapping("/person/update/{firstName}/{lastName}")
	public void updateOnePerson(@PathVariable String firstName, @PathVariable String lastName) throws IOException {
		personService.updateOnePerson(firstName, lastName);
	}

//	@DeleteMapping("/person/delete/{firstName}/{lastName}") {}

}
