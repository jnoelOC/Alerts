package com.safetynet.alerts.controller;

import java.util.List;
import java.util.SortedMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.IFirestationService;
import com.safetynet.alerts.service.IPersonService;

@RestController
public class UrlController {
	public static final Logger logger = LogManager.getLogger(UrlController.class);

	@Autowired
	IFirestationService firestationService;

	@Autowired
	IPersonService personService;

	// URL1 : List of persons covered by corresponding this station
	@GetMapping("/firestation")
	public ResponseEntity<SortedMap<String, Person>> findPersonsFromFirestation(@RequestParam String stationNumber) {
		SortedMap<String, Person> sm = firestationService.getPersonsWithBirthdatesFromFirestations(stationNumber);
		if (sm == null) {
			return new ResponseEntity<>(sm, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(sm, HttpStatus.CREATED);
		}
	}

	// URL7 : Emails list of all persons from this city
	@GetMapping("/communityEmail")
	public List<String> getEmailsFrom(@RequestParam String city) {
		return personService.getAllEmailsFrom(city);
	}
}
