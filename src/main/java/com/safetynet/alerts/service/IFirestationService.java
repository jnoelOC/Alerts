package com.safetynet.alerts.service;

import java.util.List;
import java.util.SortedMap;

import com.safetynet.alerts.dto.FirestationDTO;
import com.safetynet.alerts.model.Person;

public interface IFirestationService {

	public List<FirestationDTO> getAllFirestations();

	public FirestationDTO getOneFirestation(String station, String address);

	public List<FirestationDTO> getSeveralFirestations(String station);

	public FirestationDTO updateOneFirestation(FirestationDTO firestationDTO);

	public boolean deleteOneFirestation(FirestationDTO firestationDTO);

	public SortedMap<String, Person> getPersonsWithBirthdatesFromFirestations(String stationNumber);

	public FirestationDTO addFirestation(FirestationDTO firestationDTO);
}
