package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.IMedicalRecordRepository;
import com.safetynet.alerts.utils.MedicalRecordMapper;

@Service
public class MedicalRecordService implements IMedicalRecordService {
	@Autowired
	IMedicalRecordRepository medicalrecordRepository;

	private MedicalRecordMapper medicalrecordMapper = new MedicalRecordMapper();

	@Override
	public List<MedicalRecordDTO> getAllMedicalRecords() {
		List<MedicalRecord> listNotDto = medicalrecordRepository.findAllMedicalRecords();
		List<MedicalRecordDTO> listMedicalRecordDto = new ArrayList<MedicalRecordDTO>();

		for (MedicalRecord medicalrecord : listNotDto) {
			MedicalRecordDTO medicalrecordDTO = medicalrecordMapper.toMedicalRecordDTO(medicalrecord);
			listMedicalRecordDto.add(medicalrecordDTO);
		}

		return listMedicalRecordDto;
	}

	@Override
	public MedicalRecord addMedicalRecord(MedicalRecordDTO medicalrecordDTO) {
		MedicalRecord medicalrecord = medicalrecordMapper.toMedicalRecord(medicalrecordDTO);
		return this.medicalrecordRepository.save(medicalrecord);
	}

	@Override
	public MedicalRecord getOneMedicalRecord(String firstName, String lastName) {
		return this.medicalrecordRepository.readAMedicalRecord(firstName, lastName);
	}

	@Override
	public MedicalRecord updateOneMedicalRecord(MedicalRecordDTO medicalrecordDTO) {
		MedicalRecord medicalrecord = medicalrecordMapper.toMedicalRecord(medicalrecordDTO);
		return medicalrecordRepository.updateAMedicalRecord(medicalrecord);
	}

	@Override
	public void deleteOneMedicalRecord(MedicalRecord medicalrecord) {
		medicalrecordRepository.deleteAMedicalRecord(medicalrecord);
	}
}
