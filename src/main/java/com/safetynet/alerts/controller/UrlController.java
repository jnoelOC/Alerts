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

import com.safetynet.alerts.dto.ChildrenInfoDTO;
import com.safetynet.alerts.service.IFirestationService;
import com.safetynet.alerts.service.IPersonInfoService;
import com.safetynet.alerts.service.IPersonService;
import com.safetynet.alerts.service.PersonInfoService;

@RestController
public class UrlController {
	public static final Logger logger = LogManager.getLogger(UrlController.class);

	@Autowired
	IFirestationService firestationService;
	@Autowired
	IPersonService personService;
	@Autowired
	IPersonInfoService personInfoService;

	// URL1 : List of persons covered by this corresponding station
	@GetMapping("/firestation")
	public ResponseEntity<PersonInfoService> findPersonsFromFirestation(@RequestParam String stationNumber) {
		PersonInfoService pis = null;
		try {
			pis = firestationService.getPersonsWithBirthdatesFromFirestations(stationNumber);

		} catch (Exception ex) {

			logger.error(MessageFormat.format("Error url1 : {0}.", ex.getMessage()));
		}
		if (pis == null) {
			return new ResponseEntity<>(pis, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(pis, HttpStatus.FOUND);
		}
	}

	// URL2 : List of children living at this address
	@GetMapping("/childAlert")
	public ResponseEntity<ChildrenInfoDTO> findChildrenFromAddress(@RequestParam String address) {
		ChildrenInfoDTO cid = null;

		cid = personService.getChildrenFrom(address);

		if (cid == null) {
			return new ResponseEntity<>(cid, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(cid, HttpStatus.FOUND);
		}
	}

	// URL7 : Emails list of all persons from this city
	@GetMapping("/communityEmail")
	public List<String> getEmailsFrom(@RequestParam String city) {
		return personService.getAllEmailsFrom(city);
	}
}
