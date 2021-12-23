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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.exception.MRAlreadyCreatedException;
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

		if (lmr.isEmpty()) {
			logger.error("Get MR vide.");
			throw new MRNotFoundException();
		}
		logger.info("Get MR trouvées.");
		return lmr;
	}

	@GetMapping("/medicalrecord")
	public ResponseEntity<MedicalRecordDTO> findOneMedicalRecord(@RequestParam String firstName,
			@RequestParam String lastName) {

		MedicalRecordDTO oneMrDTO = medicalrecordService.getOneMedicalRecord(firstName, lastName);
		if (oneMrDTO == null) {
			logger.error("Erreur dans Get MedicalRecord : status Non trouvé.");
			return new ResponseEntity<>(oneMrDTO, HttpStatus.NOT_FOUND);
		} else {
			logger.info("Get : MedicalRecord trouvé.");
			return new ResponseEntity<>(oneMrDTO, HttpStatus.FOUND);
		}
	}

	@PutMapping("/medicalrecord/update")
	public ResponseEntity<MedicalRecordDTO> updateMedicalRecord(
			@Validated @RequestBody MedicalRecordDTO medicalrecordDTO) {

		MedicalRecordDTO mrDTO = medicalrecordService.updateOneMedicalRecord(medicalrecordDTO);

		if (mrDTO == null) {
			logger.error("Erreur dans update MR : status Non modifié.");
			return new ResponseEntity<>(mrDTO, HttpStatus.NOT_MODIFIED);
		} else {
			logger.info("Update MedicalRecord : Ok.");
			return new ResponseEntity<>(mrDTO, HttpStatus.OK);
		}
	}

	@DeleteMapping("/medicalrecord/delete")
	public ResponseEntity<Void> deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
		boolean isRemoved = false;
		try {
			MedicalRecordDTO medicalrecordDTO = medicalrecordService.getOneMedicalRecord(firstName, lastName);
			if (medicalrecordDTO != null) {
				isRemoved = medicalrecordService.deleteOneMedicalRecord(medicalrecordDTO);
			}
		} catch (NullPointerException e) {
			logger.error(String.format("Error : impossible de supprimer ce medical record. %s ", e));
		} catch (Exception e) {
			// logger
		}

		if (isRemoved) {
			logger.info("Delete MedicalRecord : Ok.");
			return new ResponseEntity<>(HttpStatus.FOUND);
		} else {
			logger.error("Erreur dans delete MR : status Non trouvé.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/medicalrecord/create")
	public ResponseEntity<MedicalRecordDTO> createMedicalRecordWithBodyParam(
			@Validated @RequestBody MedicalRecordDTO medicalrecordDTO) {

		MedicalRecordDTO mrDTO = medicalrecordService.addMedicalRecord(medicalrecordDTO);

		if (mrDTO == null) {
			logger.error("Erreur dans create MR : status Déjà créé.");
			throw new MRAlreadyCreatedException();
		} else {
			logger.info("Create MedicalRecord : status Créé.");
			return new ResponseEntity<>(mrDTO, HttpStatus.CREATED);
		}

	}
}