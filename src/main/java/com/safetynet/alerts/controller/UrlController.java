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
import com.safetynet.alerts.service.UrlService;

@RestController
public class UrlController {
	public static final Logger logger = LogManager.getLogger(UrlController.class);

	@Autowired
	UrlService us;

	// URL1 : List of persons covered by this corresponding station
	@GetMapping("/firestation")
	public ResponseEntity<PersonsInfoDTO> findPersonsFromFirestation(@RequestParam String stationNumber) {
		PersonsInfoDTO psid = null;

		psid = us.getPersonsWithBirthdatesFromFirestations(stationNumber);

		if (psid == null) {
			return new ResponseEntity<>(psid, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(psid, HttpStatus.FOUND);
		}
	}

	// URL2 : List of children living at this address
	@GetMapping("/childAlert")
	public ResponseEntity<ChildrenInfoDTO> findChildrenFromAddress(@RequestParam String address) {
		ChildrenInfoDTO cid = null;

		cid = us.getChildrenFrom(address);

		if (cid == null) {
			return new ResponseEntity<>(cid, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(cid, HttpStatus.FOUND);
		}
	}

	// URL3 : List of person's phones covered by this corresponding station
	@GetMapping("/phoneAlert")
	public ResponseEntity<Set<String>> findPhonesFromFirestation(@RequestParam String stationNumber) {
		Set<String> listOfPhones = null;

		listOfPhones = us.getPhonesFromFirestations(stationNumber);

		if (listOfPhones == null) {
			return new ResponseEntity<>(listOfPhones, HttpStatus.NOT_FOUND);
		} else {
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
			return new ResponseEntity<>(listOfPersons, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(listOfPersons, HttpStatus.FOUND);
		}
	}

	// URL5 : Person associated with list of firestation numbers
	@GetMapping("/flood/stations")
	public ResponseEntity<List<PersonUrl4DTO>> findFamilyWith(@RequestParam List<String> stations) {
		List<PersonUrl4DTO> lp5 = null;

		lp5 = us.getFamilyWith(stations);

		if (lp5 == null) {
			// logg error
			return new ResponseEntity<>(lp5, HttpStatus.NOT_FOUND);
		} else {
			// logg info
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
			return new ResponseEntity<>(lp6, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(lp6, HttpStatus.FOUND);
		}
	}

	// URL7 : Emails list of all persons from this city
	@GetMapping("/communityEmail")
	public List<String> getEmailsFrom(@RequestParam String city) {
		return us.getAllEmailsFrom(city);
	}
}
