package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.dto.MedicalRecordDTO;

public interface IMedicalRecordService {
	public List<MedicalRecordDTO> getAllMedicalRecords();

	public MedicalRecordDTO addMedicalRecord(MedicalRecordDTO medicalrecordDTO);

	public MedicalRecordDTO getOneMedicalRecord(String firstName, String lastName);

	public MedicalRecordDTO updateOneMedicalRecord(MedicalRecordDTO medicalrecordDTO);

	public boolean deleteOneMedicalRecord(MedicalRecordDTO medicalrecordDTO);
}
