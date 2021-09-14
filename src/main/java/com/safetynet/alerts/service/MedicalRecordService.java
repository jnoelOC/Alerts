package com.safetynet.alerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.IMedicalRecordRepository;

@Service
public class MedicalRecordService implements IMedicalRecordService {
	@Autowired
	IMedicalRecordRepository medicalrecordRepository;

	@Override
	public List<MedicalRecord> getAllMedicalRecords() {
		return medicalrecordRepository.findAllMedicalRecords();
	}

	@Override
	public MedicalRecord addMedicalRecord(MedicalRecord medicalrecord) {
		return this.medicalrecordRepository.save(medicalrecord);
	}

	@Override
	public MedicalRecord getOneMedicalRecord(String firstName, String lastName) {
		return this.medicalrecordRepository.readAMedicalRecord(firstName, lastName);
	}

	@Override
	public MedicalRecord updateOneMedicalRecord(MedicalRecord medicalrecord) {
		return medicalrecordRepository.updateAMedicalRecord(medicalrecord);
	}

	@Override
	public void deleteOneMedicalRecord(MedicalRecord medicalrecord) {
		medicalrecordRepository.deleteAMedicalRecord(medicalrecord);
	}
}
