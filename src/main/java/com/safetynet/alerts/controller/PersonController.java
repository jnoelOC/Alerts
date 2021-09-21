package com.safetynet.alerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.IPersonService;

@RestController
public class PersonController {

	@Autowired
	IPersonService personService;

	@GetMapping("/persons")
	public List<Person> findAllPersons() {

		return personService.findAllPersons();
	}

	@GetMapping("/person/{firstName}/{lastName}")
	public Person findOnePerson(@PathVariable String firstName, @PathVariable String lastName) {

		return personService.getOnePerson(firstName, lastName);
	}

	@PutMapping("/person/update")
	public void updatePerson(@RequestBody Person person) {

		personService.updateOnePerson(person);
	}

	@DeleteMapping("/person/delete/{firstName}/{lastName}")
	public void deletePerson(@PathVariable String firstName, @PathVariable String lastName) {

		Person person = personService.getOnePerson(firstName, lastName);
		personService.deleteOnePerson(person);
	}

	@PostMapping("/person/create")
	public void createPersonWithBodyParam(@RequestBody Person person) {

		personService.addPerson(person);
	}
}
