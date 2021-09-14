package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.model.MedicalRecord;

public interface IMedicalRecordService {
	public List<MedicalRecord> getAllMedicalRecords();

	public MedicalRecord addMedicalRecord(MedicalRecord medicalrecord);

	public MedicalRecord getOneMedicalRecord(String firstName, String lastName);

	public MedicalRecord updateOneMedicalRecord(MedicalRecord medicalrecord);

	public void deleteOneMedicalRecord(MedicalRecord medicalrecord);
}
