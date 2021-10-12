package com.safetynet.alerts.controller;

import java.text.MessageFormat;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.service.IFirestationService;
import com.safetynet.alerts.service.IPersonService;
import com.safetynet.alerts.utils.PersonInfo;

@RestController
public class UrlController {
	public static final Logger logger = LogManager.getLogger(UrlController.class);

	@Autowired
	IFirestationService firestationService;

	@Autowired
	IPersonService personService;

	// URL1 : List of persons covered by corresponding this station
	@GetMapping("/firestation")
	public ResponseEntity<List<PersonInfo>> findPersonsFromFirestation(@RequestParam String stationNumber) {
		List<PersonInfo> lpi = null;
		try {
			lpi = firestationService.getPersonsWithBirthdatesFromFirestations(stationNumber);

		} catch (Exception ex) {

			logger.error(MessageFormat.format("Error url1 : {0}.", ex.getMessage()));
		}
		if (lpi == null) {
			return new ResponseEntity<>(lpi, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(lpi, HttpStatus.FOUND);
		}
	}

	// URL7 : Emails list of all persons from this city
	@GetMapping("/communityEmail")
	public List<String> getEmailsFrom(@RequestParam String city) {
		return personService.getAllEmailsFrom(city);
	}
}
