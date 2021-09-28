package com.safetynet.alerts.controller;

import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.IFirestationService;

@RestController
public class UrlController {
	public static final Logger logger = LogManager.getLogger(UrlController.class);

	@Autowired
	IFirestationService firestationService;

	// URL1 : List of persons covered by corresponding station
	@GetMapping("/firestation")
	public TreeMap<String, Person> findPersonsFromFirestation(@RequestParam String stationNumber) {

		return firestationService.getPersonsFromFirestations(stationNumber);
	}
}
