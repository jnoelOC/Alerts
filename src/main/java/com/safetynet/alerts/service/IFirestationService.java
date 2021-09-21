package com.safetynet.alerts.service;

import java.util.List;
import java.util.TreeMap;

import com.safetynet.alerts.dto.FirestationDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;

public interface IFirestationService {

	public List<FirestationDTO> getAllFirestations();

	public Firestation getOneFirestation(String station, String address);

	public List<Firestation> getSeveralFirestations(String station);

	public void updateOneFirestation(FirestationDTO firestationDTO);

	public void deleteOneFirestation(Firestation firestation);

	public TreeMap<String, Person> getPersonsFromFirestations(String stationNumber);

	public void addFirestation(FirestationDTO firestationDTO);
}
