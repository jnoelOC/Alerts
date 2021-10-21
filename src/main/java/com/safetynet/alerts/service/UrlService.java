package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.IMedicalRecordRepository;
import com.safetynet.alerts.repository.IPersonRepository;
import com.safetynet.alerts.utils.MedicalRecordMapper;
import com.safetynet.alerts.utils.PersonMapper;

@Service
public class UrlService {

	@Autowired
	IPersonService personService;
	private IFirestationService firestationService;
	private IMedicalRecordService medicalRecordService;
	@Autowired
	IPersonRepository personRepository;
	@Autowired
	IMedicalRecordRepository medicalRecordRepository;
	PersonMapper personMapper = new PersonMapper();
	MedicalRecordMapper medicalRecordMapper = new MedicalRecordMapper();

	// List<PersonDTO> listOfAllPersonsDTO = personService.findAllPersons();
//	List<FirestationDTO> listOfAllFirestationsDTO = firestationService.getAllFirestations();

	public List<MedicalRecordDTO> findAllMedicalRecord() {
		List<MedicalRecordDTO> listOfAllMedRecDTO = new ArrayList<>();
		for (MedicalRecord oneMedRec : medicalRecordRepository.findAllMedicalRecords()) {
			listOfAllMedRecDTO.add(medicalRecordMapper.toMedicalRecordDTO(oneMedRec));
		}
		return listOfAllMedRecDTO;
	}

	/// URL2 //////////////////////////////////////////////////////////////
	public List<PersonDTO> retrieveAllPersonsFrom(String address) {
		// Retrieve list of all persons from address
		List<PersonDTO> listOfPersonsDTOinput = new ArrayList<>();
		List<PersonDTO> listOfPersonsDTOoutput = new ArrayList<>();

		for (Person onePerson : personRepository.findAllPersons()) {
			listOfPersonsDTOinput.add(personMapper.toPersonDTO(onePerson));
		}

		for (PersonDTO personDTO : listOfPersonsDTOinput) {
			if (personDTO.getAddress().equalsIgnoreCase(address)) {

				listOfPersonsDTOoutput.add(personDTO);
			}
		}
		return listOfPersonsDTOoutput;
	}
}
