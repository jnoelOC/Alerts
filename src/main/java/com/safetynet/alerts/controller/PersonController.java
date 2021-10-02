package com.safetynet.alerts.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.safetynet.alerts.exception.PAlreadyCreatedException;
import com.safetynet.alerts.exception.PNotFoundException;
import com.safetynet.alerts.service.IPersonService;

@RestController
public class PersonController {

	public static final Logger logger = LogManager.getLogger(PersonController.class);

	@Autowired
	IPersonService personService;

	@GetMapping("/persons")
	public List<PersonDTO> findAllPersons() {

		List<PersonDTO> lpDto = personService.findAllPersons();

		if (lpDto.isEmpty() || lpDto == null) {
			throw new PNotFoundException();
		}
		return lpDto;
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

		PersonDTO pDTO = personService.updateOnePerson(personDTO);
		if (pDTO == null) {
			return new ResponseEntity<>(pDTO, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(pDTO, HttpStatus.OK);
		}
	}

	@DeleteMapping("/person/delete/{firstName}/{lastName}")
	public ResponseEntity<Void> deletePerson(@PathVariable String firstName, @PathVariable String lastName) {

		boolean isRemoved = false;
		try {
			PersonDTO personDTO = personService.getOnePerson(firstName, lastName);
			isRemoved = personService.deleteOnePerson(personDTO);
		} catch (NullPointerException e) {
			logger.error("Error : impossible de supprimer la personne. %s ", e.toString());
		} catch (Exception ex) {
			logger.error("Error : %s ", ex.toString());
		}

		if (isRemoved) {
			return new ResponseEntity<>(HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("/person/create")
	public ResponseEntity<PersonDTO> createPersonWithBodyParam(@RequestBody PersonDTO personDTO) {

		PersonDTO pDTO = personService.addPerson(personDTO);
		if (pDTO == null) {
			// return new ResponseEntity<>(pDTO, HttpStatus.NOT_FOUND);
			throw new PAlreadyCreatedException();
		} else {
			return new ResponseEntity<>(pDTO, HttpStatus.CREATED);
		}

	}

}
