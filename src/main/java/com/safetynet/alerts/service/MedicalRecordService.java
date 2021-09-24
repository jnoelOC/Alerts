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

		// MedicalRecordDTO mrDTO =
		// medicalrecordMapper.toMedicalRecordDTO(medicalrecord);
		// return ResponseEntity.ok().header("headerNameService",
		// "headerValueService").body(medicalrecordDTO);
	}

	@Override
	public MedicalRecordDTO getOneMedicalRecord(String firstName, String lastName) {

		MedicalRecord medicalrecord = this.medicalrecordRepository.readAMedicalRecord(firstName, lastName);
		MedicalRecordDTO medicalrecordDTO = medicalrecordMapper.toMedicalRecordDTO(medicalrecord);
		return medicalrecordDTO;
		// return this.medicalrecordRepository.readAMedicalRecord(firstName, lastName);
	}

	@Override
	public MedicalRecord updateOneMedicalRecord(MedicalRecordDTO medicalrecordDTO) {
		MedicalRecord medicalrecord = medicalrecordMapper.toMedicalRecord(medicalrecordDTO);
		return medicalrecordRepository.updateAMedicalRecord(medicalrecord);
	}

	@Override
	public boolean deleteOneMedicalRecord(MedicalRecordDTO medicalrecordDTO) {
		MedicalRecord medicalrecord = medicalrecordMapper.toMedicalRecord(medicalrecordDTO);
		return medicalrecordRepository.deleteAMedicalRecord(medicalrecord);
	}
}
