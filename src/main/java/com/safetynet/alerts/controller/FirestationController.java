package com.safetynet.alerts.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.FirestationDTO;
import com.safetynet.alerts.exception.FAlreadyCreatedException;
import com.safetynet.alerts.service.IFirestationService;

@RestController
public class FirestationController {

	public static final Logger logger = LogManager.getLogger(FirestationController.class);

	@Autowired
	IFirestationService firestationService;

	@GetMapping("/firestations")
	public List<FirestationDTO> findAllFirestations() {
		logger.info("Get Casernes trouvées.");
		return firestationService.getAllFirestations();
	}

	@GetMapping("/firestation/{station}")
	public List<FirestationDTO> findSeveralFirestation(@PathVariable String station) {
		logger.info("Get : Caserne num " + station + " trouvée.");
		return firestationService.getSeveralFirestations(station);

	}

	@GetMapping("/firestation/{station}/{address}")
	public ResponseEntity<FirestationDTO> findOneFirestation(@PathVariable String station,
			@PathVariable String address) {

		FirestationDTO firestationDTO = firestationService.getOneFirestation(station, address);
		if (firestationDTO == null) {
			logger.error("Erreur dans Get caserne : status Non trouvé.");
			return new ResponseEntity<>(firestationDTO, HttpStatus.NOT_FOUND);
		} else {
			logger.info("Get : Caserne trouvée.");
			return new ResponseEntity<>(firestationDTO, HttpStatus.FOUND);
		}

	}

	@PutMapping("/firestation/update")
	public ResponseEntity<FirestationDTO> updateFirestation(@RequestBody FirestationDTO firestationDTO) {

		FirestationDTO fDTO = firestationService.updateOneFirestation(firestationDTO);
		if (fDTO == null) {
			logger.error("Erreur dans update caserne : status Non trouvé.");
			return new ResponseEntity<>(fDTO, HttpStatus.NOT_MODIFIED);
		} else {
			logger.info("Update caserne : Ok.");
			return new ResponseEntity<>(fDTO, HttpStatus.OK);
		}
	}

	@DeleteMapping("/firestation/delete")
	public ResponseEntity<Void> deleteFirestation(@RequestParam String address, @RequestParam String station) {
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
			logger.info("Delete caserne : Ok.");
			return new ResponseEntity<>(HttpStatus.FOUND);
		} else {
			logger.error("Erreur dans delete caserne : status Non trouvé.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/firestation/create")
	public ResponseEntity<FirestationDTO> createFirestationWithBodyParam(@RequestBody FirestationDTO firestationDTO) {

		FirestationDTO fDTO = firestationService.addFirestation(firestationDTO);
		if (fDTO == null) {
			logger.error("Erreur dans create caserne : status Non trouvé.");
			// return new ResponseEntity<>(fDTO, HttpStatus.NOT_FOUND);
			throw new FAlreadyCreatedException();
		} else {
			logger.info("Create caserne : Ok.");
			return new ResponseEntity<>(fDTO, HttpStatus.CREATED);
		}
	}
}
