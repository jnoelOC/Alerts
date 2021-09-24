package com.safetynet.alerts.repository;

import java.util.List;

import com.safetynet.alerts.model.MedicalRecord;

public interface IMedicalRecordRepository {
	public MedicalRecord save(MedicalRecord medicalrecord);

	public List<MedicalRecord> findAllMedicalRecords();

	public MedicalRecord readAMedicalRecord(String firstName, String lastName);

	public MedicalRecord updateAMedicalRecord(MedicalRecord medicalrecord);

	public boolean deleteAMedicalRecord(MedicalRecord medicalrecord);
}
