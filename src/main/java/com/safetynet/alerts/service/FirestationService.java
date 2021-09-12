package com.safetynet.alerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.IFirestationRepository;

@Service
public class FirestationService implements IFirestationService {
	@Autowired
	IFirestationRepository firestationRepository;

	public List<Firestation> getAllFirestations() {
		return firestationRepository.findAllFirestations();

	}

	public Firestation addFirestation(Firestation firestation) {
		return this.firestationRepository.save(firestation);
	}

	public Firestation getOneFirestation(String Station, String Address) {
		return this.firestationRepository.readAFirestation(Station, Address);
	}

	public List<Firestation> getSeveralFirestations(String Station) {
		return this.firestationRepository.readSeveralFirestations(Station);
	}

	public Firestation updateOneFirestation(Firestation firestation) {
		return firestationRepository.updateAFirestation(firestation);
	}

	public void deleteOneFirestation(Firestation firestation) {
		firestationRepository.deleteAFirestation(firestation);
	}

}
