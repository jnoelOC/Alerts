package com.safetynet.alerts.service;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import com.safetynet.alerts.dto.ChildrenInfoDTO;
import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.dto.PersonUrl4DTO;
import com.safetynet.alerts.dto.PersonUrl6DTO;
import com.safetynet.alerts.dto.PersonsInfoDTO;
import com.safetynet.alerts.dto.PersonsUrl4DTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

public interface IUrlService {

	List<PersonUrl6DTO> getPersonWith(String firstName, String lastName);

	List<MedicalRecordDTO> findAllMedicalRecord();

	List<PersonDTO> retrieveAllPersonsFrom(String address);

	List<Person> retrievePersonsWithSameStationAddress(List<Firestation> listOfFirestations,
			List<Person> listOfPersons);

	Integer computeAge(String firstName, String lastName);

	MedicalRecordDTO fillMedicalRecordDTO(MedicalRecordDTO medRecDTO, String firstName, String lastName);

	PersonsInfoDTO getPersonsWithBirthdatesFromFirestations(String stationNumber);

	Set<String> getPhonesFromFirestations(String stationNumber);

	PersonsUrl4DTO getPersonsWithFirestationsFrom(String address);

	ChildrenInfoDTO getChildrenFrom(String address);

	List<Person> retrieveAListOfPersonsFrom(TreeMap<String, String> listOfPersons);

	MedicalRecord retrieveMedRec(String firstName, String lastName);

	List<PersonUrl4DTO> getFamilyWith(List<String> listOfFirestationNb);

	List<String> getAllEmailsFrom(String city);

}
