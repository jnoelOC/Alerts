package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.IMedicalRecordRepository;
import com.safetynet.alerts.utils.MedicalRecordMapper;

@Service
public class MedicalRecordService implements IMedicalRecordService {

	public static final Logger logger = LogManager.getLogger(MedicalRecordService.class);

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
	public MedicalRecordDTO addMedicalRecord(MedicalRecordDTO medicalrecordDTO) {

		MedicalRecord medicalrecord = medicalrecordMapper.toMedicalRecord(medicalrecordDTO);
		MedicalRecord mr = this.medicalrecordRepository.save(medicalrecord);
		return medicalrecordMapper.toMedicalRecordDTO(mr);
	}

	@Override
	public MedicalRecordDTO getOneMedicalRecord(String firstName, String lastName) {

		MedicalRecordDTO medicalrecordDTO;
		MedicalRecord medicalrecord = this.medicalrecordRepository.readAMedicalRecord(firstName, lastName);
		if (medicalrecord == null) {
			medicalrecordDTO = null;
		} else {
			medicalrecordDTO = medicalrecordMapper.toMedicalRecordDTO(medicalrecord);
		}
		return medicalrecordDTO;
		// return this.medicalrecordRepository.readAMedicalRecord(firstName, lastName);
	}

	@Override
	public MedicalRecordDTO updateOneMedicalRecord(MedicalRecordDTO medicalrecordDTO) {
		MedicalRecord mr = medicalrecordMapper.toMedicalRecord(medicalrecordDTO);
		MedicalRecord medicalrecord = medicalrecordRepository.updateAMedicalRecord(mr);
		return medicalrecordMapper.toMedicalRecordDTO(medicalrecord);
	}

	@Override
	public boolean deleteOneMedicalRecord(MedicalRecordDTO medicalrecordDTO) {
		MedicalRecord medicalrecord = medicalrecordMapper.toMedicalRecord(medicalrecordDTO);
		return medicalrecordRepository.deleteAMedicalRecord(medicalrecord);
	}
}
