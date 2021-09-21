package com.safetynet.alerts.service;

import java.util.List;
import java.util.TreeMap;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;

public interface IFirestationService {

	public List<Firestation> getAllFirestations();

	public Firestation getOneFirestation(String Station, String Address);

	public List<Firestation> getSeveralFirestations(String Station);

	public void updateOneFirestation(Firestation firestation);

	public void deleteOneFirestation(Firestation firestation);

	public TreeMap<String, Person> getPersonsFromFirestations(String stationNumber);

	public void addFirestation(Firestation firestation);
}
