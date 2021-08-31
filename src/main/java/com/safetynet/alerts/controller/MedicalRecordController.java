package com.safetynet.alerts.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.service.JacksonService;

@RestController
public class MedicalRecordController {
	JacksonService player = new JacksonService();
	MedicalRecords allMedicalRecords = player.ReadMedicalRecordsFromJson();

	@GetMapping("/medicalrecords")
	public List<MedicalRecord> findAllMedicalRecords() {

		return allMedicalRecords.getMedicalrecords();
	}
}
