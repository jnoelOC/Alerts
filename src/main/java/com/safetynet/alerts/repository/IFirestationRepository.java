package com.safetynet.alerts.repository;

import java.util.List;

import com.safetynet.alerts.model.Firestation;

public interface IFirestationRepository {

	public List<Firestation> findAllFirestations();

	public Firestation save(Firestation firestation);

	public Firestation readAFirestation(String station, String Address);

	public List<Firestation> readSeveralFirestations(String Station);

	public Firestation updateAFirestation(Firestation firestation);

	public void deleteAFirestation(Firestation firestation);
}
