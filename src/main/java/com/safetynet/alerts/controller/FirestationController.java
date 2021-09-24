package com.safetynet.alerts.controller;

import java.util.List;

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

import com.safetynet.alerts.dto.FirestationDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.IFirestationService;

@RestController
public class FirestationController {

	@Autowired
	IFirestationService firestationService;

	@GetMapping("/firestations")
	public List<FirestationDTO> findAllFirestations() {

		return firestationService.getAllFirestations();
	}

	@GetMapping("/firestation/{station}")
	public List<Firestation> findSeveralFirestation(@PathVariable String station) {

		return firestationService.getSeveralFirestations(station);

	}

	@GetMapping("/firestation/{station}/{address}")
	public ResponseEntity<FirestationDTO> findOneFirestation(@PathVariable String station,
			@PathVariable String address) {

		FirestationDTO firestationDTO = firestationService.getOneFirestation(station, address);
		if (firestationDTO == null) {
			return new ResponseEntity<>(firestationDTO, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(firestationDTO, HttpStatus.FOUND);
		}

	}

	@PutMapping("/firestation/update")
	public ResponseEntity<FirestationDTO> updateFirestation(@RequestBody FirestationDTO firestationDTO) {

		Firestation firestation = firestationService.updateOneFirestation(firestationDTO);
		if (firestation == null) {
			return new ResponseEntity<>(firestationDTO, HttpStatus.NOT_MODIFIED);
		} else {
			return new ResponseEntity<>(firestationDTO, HttpStatus.OK);
		}
	}

	@DeleteMapping("/firestation/delete/{station}/{address}")
	public ResponseEntity<Void> deleteFirestation(@PathVariable String station, @PathVariable String address) {
		boolean isRemoved = false;
		try {
			FirestationDTO firestationDTO = firestationService.getOneFirestation(address, station);
			isRemoved = firestationService.deleteOneFirestation(firestationDTO);

		} catch (NullPointerException e) {
			// logger
		} catch (Exception e) {
			// logger
		}

		if (isRemoved) {
			return new ResponseEntity<>(HttpStatus.GONE);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/firestation/create")
	public ResponseEntity<FirestationDTO> createFirestationWithBodyParam(@RequestBody FirestationDTO firestationDTO) {

		Firestation firestation = firestationService.addFirestation(firestationDTO);
		if (firestation == null) {
			return new ResponseEntity<>(firestationDTO, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(firestationDTO, HttpStatus.CREATED);
		}
	}
}
