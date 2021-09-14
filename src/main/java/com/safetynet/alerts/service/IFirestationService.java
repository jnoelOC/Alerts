package com.safetynet.alerts.service;

import java.util.List;
import java.util.TreeMap;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;

public interface IFirestationService {

	public List<Firestation> getAllFirestations();

	public Firestation addFirestation(Firestation firestation);

	public Firestation getOneFirestation(String Station, String Address);

	public List<Firestation> getSeveralFirestations(String Station);

	public Firestation updateOneFirestation(Firestation firestation);

	public void deleteOneFirestation(Firestation firestation);

	public TreeMap<String, Person> getPersonsFromFirestations(String stationNumber);
}
