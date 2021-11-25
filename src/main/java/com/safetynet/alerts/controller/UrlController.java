package com.safetynet.alerts.controller;

import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.ChildrenInfoDTO;
import com.safetynet.alerts.dto.PersonUrl4DTO;
import com.safetynet.alerts.dto.PersonUrl6DTO;
import com.safetynet.alerts.dto.PersonsInfoDTO;
import com.safetynet.alerts.dto.PersonsUrl4DTO;
import com.safetynet.alerts.service.IUrlService;

@RestController
public class UrlController {
	public static final Logger logger = LogManager.getLogger(UrlController.class);

	private final IUrlService us;

	@Autowired
	public UrlController(final IUrlService urlService) {
		this.us = urlService;
	}

	// URL1 : List of persons covered by this corresponding station
	@GetMapping("/firestation")
	public ResponseEntity<PersonsInfoDTO> findPersonsFromFirestation(@RequestParam String stationNumber) {
		PersonsInfoDTO psid = null;

		psid = us.getPersonsWithBirthdatesFromFirestations(stationNumber);

		if (psid == null) {
			logger.error("Erreur dans Url1 : status Non trouvé.");
			return new ResponseEntity<>(psid, HttpStatus.NOT_FOUND);
		} else {
			logger.info("Url1 : Liste de personnes trouvées.");
			return new ResponseEntity<>(psid, HttpStatus.FOUND);
		}
	}

	// URL2 : List of children living at this address
	@GetMapping("/childAlert")
	public ResponseEntity<ChildrenInfoDTO> findChildrenFromAddress(@RequestParam String address) {
		ChildrenInfoDTO cid = null;

		cid = us.getChildrenFrom(address);

		if (cid == null) {
			logger.error("Erreur dans Url2 : status Non trouvé.");
			return new ResponseEntity<>(cid, HttpStatus.NOT_FOUND);
		} else {
			logger.info("Url2 : Liste de enfants trouvés.");
			return new ResponseEntity<>(cid, HttpStatus.FOUND);
		}
	}

	// URL3 : List of person's phones covered by this corresponding station
	@GetMapping("/phoneAlert")
	public ResponseEntity<Set<String>> findPhonesFromFirestation(@RequestParam String stationNumber) {
		Set<String> listOfPhones = null;

		listOfPhones = us.getPhonesFromFirestations(stationNumber);

		if (listOfPhones == null) {
			logger.error("Erreur dans Url3 : status Non trouvé.");
			return new ResponseEntity<>(listOfPhones, HttpStatus.NOT_FOUND);
		} else {
			logger.info("Url3 : Liste de téléphones trouvés.");
			return new ResponseEntity<>(listOfPhones, HttpStatus.FOUND);
		}
	}

	// URL4 : List of persons living in address associated with this corresponding
	// station address
	@GetMapping("/fire")
	public ResponseEntity<PersonsUrl4DTO> findPersonsWithFirestationFrom(@RequestParam String address) {
		PersonsUrl4DTO listOfPersons = null;

		listOfPersons = us.getPersonsWithFirestationsFrom(address);

		if (listOfPersons == null) {
			logger.error("Erreur dans Url4 : status Non trouvé.");
			return new ResponseEntity<>(listOfPersons, HttpStatus.NOT_FOUND);
		} else {
			logger.info("Url4 : Liste de personnes trouvées.");
			return new ResponseEntity<>(listOfPersons, HttpStatus.FOUND);
		}
	}

	// URL5 : Person associated with list of firestation numbers
	@GetMapping("/flood/stations")
	public ResponseEntity<List<PersonUrl4DTO>> findFamilyWith(@RequestParam List<String> stations) {
		List<PersonUrl4DTO> lp5 = null;

		lp5 = us.getFamilyWith(stations);

		if (lp5 == null) {
			logger.error("Erreur dans Url5 : status Non trouvé.");
			return new ResponseEntity<>(lp5, HttpStatus.NOT_FOUND);
		} else {
			logger.info("Url5 : Liste de personnes trouvées.");
			return new ResponseEntity<>(lp5, HttpStatus.FOUND);
		}
	}

	// URL6 : Person associated with these firstname and lastname
	@GetMapping("/personInfo")
	public ResponseEntity<List<PersonUrl6DTO>> findPersonWith(@RequestParam String firstName,
			@RequestParam String lastName) {
		List<PersonUrl6DTO> lp6 = null;

		lp6 = us.getPersonWith(firstName, lastName);

		if (lp6 == null) {
			logger.error("Url6 : status Non trouvé.");
			return new ResponseEntity<>(lp6, HttpStatus.NOT_FOUND);
		} else {
			logger.info("Url6 : Liste de personnes trouvées.");
			return new ResponseEntity<>(lp6, HttpStatus.FOUND);
		}
	}

	// URL7 : Emails list of all persons from this city
	@GetMapping("/communityEmail")
	public List<String> getEmailsFrom(@RequestParam String city) {
		List<String> ls = null;

		ls = us.getAllEmailsFrom(city);

		if (ls == null) {
			logger.error("Erreur dans Url7 : status Non trouvé.");
		} else {
			logger.info("Url7 : Liste de mails trouvés.");
		}

		return ls;

	}
}
