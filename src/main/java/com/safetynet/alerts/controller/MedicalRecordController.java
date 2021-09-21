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
	public MedicalRecord findOneMedicalRecord(@PathVariable String firstName, @PathVariable String lastName) {

		return medicalrecordService.getOneMedicalRecord(firstName, lastName);

	}

	@PutMapping("/medicalrecord/update")
	public void updateMedicalRecord(@RequestBody MedicalRecordDTO medicalrecordDTO) {

		medicalrecordService.updateOneMedicalRecord(medicalrecordDTO);
	}

	@DeleteMapping("/medicalrecord/delete/{firstName}/{lastName}")
	public void deleteMedicalRecord(@PathVariable String firstName, @PathVariable String lastName) {

		MedicalRecord medicalrecord = medicalrecordService.getOneMedicalRecord(firstName, lastName);
		medicalrecordService.deleteOneMedicalRecord(medicalrecord);
	}

	@PostMapping("/medicalrecord/create")
	public void createMedicalRecordWithBodyParam(@RequestBody MedicalRecordDTO medicalrecordDTO) {

		medicalrecordService.addMedicalRecord(medicalrecordDTO);
	}
}