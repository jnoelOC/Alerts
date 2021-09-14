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

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.IFirestationService;

@RestController
public class FirestationController {

	@Autowired
	IFirestationService firestationService;

	@GetMapping("/firestations")
	public List<Firestation> findAllFirestations() {

		return firestationService.getAllFirestations();
	}

	@GetMapping("/firestation/{station}")
	public List<Firestation> findSeveralFirestation(@PathVariable String station) {

		return firestationService.getSeveralFirestations(station);

	}

	@GetMapping("/firestation/{station}/{address}")
	public Firestation findOneFirestation(@PathVariable String station, @PathVariable String address) {

		return firestationService.getOneFirestation(station, address);

	}

	@PutMapping("/firestation/update/{station}/{address}")
	public void updateFirestation(@PathVariable String station, @PathVariable String address,
			@RequestBody Firestation firestation) {

		firestation = firestationService.getOneFirestation(address, station);

		firestationService.updateOneFirestation(firestation);
	}

	@DeleteMapping("/firestation/delete/{station}/{address}")
	public void deleteFirestation(@PathVariable String station, @PathVariable String address) {

		Firestation firestation = firestationService.getOneFirestation(address, station);
		firestationService.deleteOneFirestation(firestation);
	}

	@PostMapping("/firestation/create/{station}/{address}")
	public void createFirestation(@PathVariable String station, @PathVariable String address) {

		Firestation firestation = new Firestation(station, address);
		firestationService.addFirestation(firestation);
	}

}
