package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.model.MedicalRecord;

public interface IMedicalRecordService {
	public List<MedicalRecordDTO> getAllMedicalRecords();

	public MedicalRecord addMedicalRecord(MedicalRecordDTO medicalrecordDTO);

	public MedicalRecord getOneMedicalRecord(String firstName, String lastName);

	public MedicalRecord updateOneMedicalRecord(MedicalRecordDTO medicalrecordDTO);

	public void deleteOneMedicalRecord(MedicalRecord medicalrecord);
}
