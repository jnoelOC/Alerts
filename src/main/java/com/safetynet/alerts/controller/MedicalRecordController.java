package com.safetynet.alerts.controller;

import java.util.List;

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
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.IMedicalRecordService;

@RestController
public class MedicalRecordController {

	@Autowired
	IMedicalRecordService medicalrecordService;

	@GetMapping("/medicalrecords")
	public List<MedicalRecordDTO> findAllMedicalRecords() {

		return medicalrecordService.getAllMedicalRecords();
	}

	@GetMapping("/medicalrecord/{firstName}/{lastName}")
	public ResponseEntity<MedicalRecordDTO> findOneMedicalRecord(@PathVariable String firstName,
			@PathVariable String lastName) {

		MedicalRecordDTO oneMrDTO = medicalrecordService.getOneMedicalRecord(firstName, lastName);
		return new ResponseEntity<>(oneMrDTO, HttpStatus.FOUND);

		// return medicalrecordService.getOneMedicalRecord(firstName, lastName);

	}

	@PutMapping("/medicalrecord/update")
	public ResponseEntity<MedicalRecordDTO> updateMedicalRecord(
			@Validated @RequestBody MedicalRecordDTO medicalrecordDTO) {

		MedicalRecord medicalrecord = medicalrecordService.updateOneMedicalRecord(medicalrecordDTO);

		if (medicalrecord == null) {
			return new ResponseEntity<>(medicalrecordDTO, HttpStatus.NOT_MODIFIED);
		} else {
			return new ResponseEntity<>(medicalrecordDTO, HttpStatus.OK);
		}
	}

	@DeleteMapping("/medicalrecord/delete/{firstName}/{lastName}")
	public ResponseEntity<Void> deleteMedicalRecord(@PathVariable String firstName, @PathVariable String lastName) {
		boolean isRemoved = false;
		try {
			MedicalRecordDTO medicalrecordDTO = medicalrecordService.getOneMedicalRecord(firstName, lastName);
			isRemoved = medicalrecordService.deleteOneMedicalRecord(medicalrecordDTO);
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

	@PostMapping("/medicalrecord/create")
	public ResponseEntity<MedicalRecordDTO> createMedicalRecordWithBodyParam(
			@Validated @RequestBody MedicalRecordDTO medicalrecordDTO) {

		MedicalRecord medicalrecord = medicalrecordService.addMedicalRecord(medicalrecordDTO);

		/// return ResponseEntity.ok().header("headerNameController",
		/// "headerValueController").body(medicalrecordDTO);

		// HttpHeaders hh = new ResponseEntity<>(medicalrecordDTO,
		// HttpStatus.CREATED).getHeaders();

		if (medicalrecord == null) {
			return new ResponseEntity<>(medicalrecordDTO, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(medicalrecordDTO, HttpStatus.CREATED);
		}

	}
}