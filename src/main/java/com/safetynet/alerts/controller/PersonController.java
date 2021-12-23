package com.safetynet.alerts.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.service.IPersonService;

@RestController
public class PersonController {

	public static final Logger logger = LogManager.getLogger(PersonController.class);

	@Autowired
	IPersonService personService;

	@GetMapping("/persons")
	public List<PersonDTO> findAllPersons() {

		List<PersonDTO> lpDto = personService.findAllPersons();

		if (lpDto.isEmpty()) {
			logger.error("Erreur dans Find all persons : status Non trouvé.");
			return null;
			// throw new PNotFoundException();
		}
		logger.info("personnes trouvées.");
		return lpDto;
	}

	@GetMapping("/person")
	public ResponseEntity<PersonDTO> findOnePerson(@RequestParam String firstName, @RequestParam String lastName) {
		PersonDTO personDTO = personService.getOnePerson(firstName, lastName);
		if (personDTO == null) {
			logger.error("Erreur dans PersonController : status Non trouvé.");
			return new ResponseEntity<>(personDTO, HttpStatus.NOT_FOUND);
		} else {
			logger.info("status personne trouvée.");
			return new ResponseEntity<>(personDTO, HttpStatus.FOUND);
		}

	}

	@PutMapping("/person/update")
	public ResponseEntity<PersonDTO> updatePerson(@RequestBody PersonDTO personDTO) {

		PersonDTO pDTO = personService.updateOnePerson(personDTO);
		if (pDTO == null) {
			logger.error("Erreur dans update personne : status Non trouvé.");
			return new ResponseEntity<>(pDTO, HttpStatus.NOT_FOUND);
		} else {
			logger.info("Update personne : status Ok.");
			return new ResponseEntity<>(pDTO, HttpStatus.OK);
		}
	}

	@DeleteMapping("/person/delete")
	public ResponseEntity<Boolean> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {

		Boolean isRemoved = false;
//		try {
		PersonDTO personDTO = personService.getOnePerson(firstName, lastName);
		isRemoved = personService.deleteOnePerson(personDTO);
//		} catch (NullPointerException e) {
//			logger.error("Error : impossible de supprimer la personne. %s ", e.toString());
//		} catch (Exception ex) {
//			logger.error("Error : %s ", ex.toString());
//		}

		if (Boolean.TRUE.equals(isRemoved)) {
			logger.info("Delete personne : status Trouvée.");
			return new ResponseEntity<>(true, HttpStatus.FOUND);
		} else {
			logger.error("Erreur dans delete personne : status Non trouvée.");
			return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("/person/create")
	public ResponseEntity<PersonDTO> createPersonWithBodyParam(@RequestBody PersonDTO personDTO) {

		PersonDTO pDTO = personService.addPerson(personDTO);
		if (pDTO == null) {
			logger.error("Erreur dans create person : status Non trouvée.");
			return new ResponseEntity<>(pDTO, HttpStatus.NOT_FOUND);
			// throw new PAlreadyCreatedException();
		} else {
			logger.info("Create personne : status Créée.");
			return new ResponseEntity<>(pDTO, HttpStatus.CREATED);
		}

	}

}
