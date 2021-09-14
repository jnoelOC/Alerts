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

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.IMedicalRecordService;

@RestController
public class MedicalRecordController {

	@Autowired
	IMedicalRecordService medicalrecordService;

	@GetMapping("/medicalrecords")
	public List<MedicalRecord> findAllMedicalRecords() {

		return medicalrecordService.getAllMedicalRecords();
	}

	@GetMapping("/medicalrecord/{firstName}/{lastName}")
	public MedicalRecord findOneMedicalRecord(@PathVariable String firstName, @PathVariable String lastName) {

		return medicalrecordService.getOneMedicalRecord(firstName, lastName);

	}

	@PutMapping("/medicalrecord/update")
	public void updateMedicalRecord(@RequestBody MedicalRecord medicalrecord) {

		medicalrecordService.updateOneMedicalRecord(medicalrecord);
	}

	@DeleteMapping("/medicalrecord/delete/{firstName}/{lastName}")
	public void deleteMedicalRecord(@PathVariable String firstName, @PathVariable String lastName) {

		MedicalRecord medicalrecord = medicalrecordService.getOneMedicalRecord(firstName, lastName);
		medicalrecordService.deleteOneMedicalRecord(medicalrecord);
	}

	@PostMapping("/medicalrecord/create/{firstName}/{lastName}")
	public void createMedicalRecord(@PathVariable String firstName, @PathVariable String lastName) {

		String[] medications = { "hydrapermazol:300mg", "dodoxadin:30mg" };
		String[] allergies = { "shellfish", "ambroisie" };
		String birthdat = "06/12/1969";
		MedicalRecord medicalrecord = new MedicalRecord(firstName, lastName, birthdat, medications, allergies);
		medicalrecordService.addMedicalRecord(medicalrecord);
	}

	@PostMapping("/medicalrecord/create")
	public void createMedicalRecordWithBodyParam(@RequestBody MedicalRecord medicalrecord) {

		medicalrecordService.addMedicalRecord(medicalrecord);
	}
}