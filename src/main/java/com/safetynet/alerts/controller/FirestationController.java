package com.safetynet.alerts.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.service.JacksonService;

@RestController
public class FirestationController {

	JacksonService player = new JacksonService();
	Firestations allFirestations = player.ReadFirestationsFromJson();

	@GetMapping("/firestations")
	public List<Firestation> findAllFirestations() {

		return allFirestations.getFirestations();
	}

}
