package com.safetynet.alerts.utils;

import org.springframework.stereotype.Component;

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.model.MedicalRecord;

@Component
public class MedicalRecordMapper {
	public MedicalRecord toMedicalRecord(MedicalRecordDTO medicalrecordDTO) {
		return new MedicalRecord(medicalrecordDTO.getFirstName(), medicalrecordDTO.getLastName(),
				medicalrecordDTO.getBirthDate(), medicalrecordDTO.getMedications(), medicalrecordDTO.getAllergies());
	}

	public MedicalRecordDTO toMedicalRecordDTO(MedicalRecord medicalrecord) {
		return new MedicalRecordDTO(medicalrecord.getFirstName(), medicalrecord.getLastName(),
				medicalrecord.getBirthDate(), medicalrecord.getMedications(), medicalrecord.getAllergies());
	}
}
