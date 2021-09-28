package com.safetynet.alerts.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.exception.MRNotFoundException;
import com.safetynet.alerts.service.IMedicalRecordService;

@RestController
public class MedicalRecordController {

	public static final Logger logger = LogManager.getLogger(MedicalRecordController.class);

	@Autowired
	IMedicalRecordService medicalrecordService;

	@GetMapping("/medicalrecords")
	public List<MedicalRecordDTO> findAllMedicalRecords() {

		List<MedicalRecordDTO> lmr = medicalrecordService.getAllMedicalRecords();
		// Faire du TDD !!!
		if (lmr.isEmpty() || lmr == null) {
			throw new MRNotFoundException();
		}
		return lmr;
	}

	@GetMapping("/medicalrecord/{firstName}/{lastName}")
	public ResponseEntity<MedicalRecordDTO> findOneMedicalRecord(@PathVariable String firstName,
			@PathVariable String lastName) {

		MedicalRecordDTO oneMrDTO = medicalrecordService.getOneMedicalRecord(firstName, lastName);
		if (oneMrDTO == null) {
			return new ResponseEntity<>(oneMrDTO, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(oneMrDTO, HttpStatus.FOUND);
		}
	}

	@PutMapping("/medicalrecord/update")
	public ResponseEntity<MedicalRecordDTO> updateMedicalRecord(
			@Validated @RequestBody MedicalRecordDTO medicalrecordDTO) {

		MedicalRecordDTO mrDTO = medicalrecordService.updateOneMedicalRecord(medicalrecordDTO);

		if (mrDTO == null) {
			return new ResponseEntity<>(mrDTO, HttpStatus.NOT_MODIFIED);
		} else {
			return new ResponseEntity<>(mrDTO, HttpStatus.OK);
		}
	}

	@DeleteMapping("/medicalrecord/delete/{firstName}/{lastName}")
	public ResponseEntity<Void> deleteMedicalRecord(@PathVariable String firstName, @PathVariable String lastName) {
		boolean isRemoved = false;
		try {
			MedicalRecordDTO medicalrecordDTO = medicalrecordService.getOneMedicalRecord(firstName, lastName);
			isRemoved = medicalrecordService.deleteOneMedicalRecord(medicalrecordDTO);
		} catch (NullPointerException e) {
			logger.error("Error : impossible de supprimer ce medical record. %s ", e);
		} catch (Exception e) {
			// logger
		}

		if (isRemoved) {
			return new ResponseEntity<>(HttpStatus.GONE);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/medicalrecord/create")
	public ResponseEntity<MedicalRecordDTO> createMedicalRecordWithBodyParam(
			@Validated @RequestBody MedicalRecordDTO medicalrecordDTO) {

		MedicalRecordDTO mrDTO = medicalrecordService.addMedicalRecord(medicalrecordDTO);

		if (mrDTO == null) {
			return new ResponseEntity<>(mrDTO, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(mrDTO, HttpStatus.CREATED);
		}

	}
}