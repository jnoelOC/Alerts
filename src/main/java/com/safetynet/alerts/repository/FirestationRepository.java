package com.safetynet.alerts.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Firestation;

@Repository
public class FirestationRepository implements IFirestationRepository {

	public static final Logger logger = LogManager.getLogger(FirestationRepository.class);

	private List<Firestation> firestations = new ArrayList<>();

	public Firestation save(Firestation firestation) {

		boolean fFound = false;

		if (firestation == null) {
			fFound = true;
		} else {
			for (Firestation fs : firestations) {
				if (fs.getAddress().equalsIgnoreCase(firestation.getAddress())) {

					fFound = true;
					break;
				}
			}
		}

		if (!fFound) {
			firestations.add(firestation);
			return firestation;
		} else { // ALREADY CREATED
			return null;
		}
	}

	public List<Firestation> findAllFirestations() {
		return firestations;
	}

	public Firestation readAFirestation(String address, String station) {

		if (station == null || address == null) {
			return null;
		}

		for (Firestation fs : firestations) {
			if (fs.getStation().equals(station) && fs.getAddress().equalsIgnoreCase(address)) {

				return fs;
			}
		}

		return null;
	}

	public List<Firestation> readSeveralFirestations(String station) {

		List<Firestation> fs = new ArrayList<>();

		if (station == null) {
			return Collections.emptyList();
		}

		for (Firestation oneFirestation : firestations) {

			if (oneFirestation.getStation().equals(station)) {

				fs.add(oneFirestation);
			}
		}

		return fs;
	}

	@Override
	public Firestation updateAFirestation(Firestation firestation) {

		if (firestation == null) {
			return null;
		}

		for (Firestation oneFirestation : firestations) {

			if (oneFirestation.getAddress().equalsIgnoreCase(firestation.getAddress())) {

				oneFirestation.setStation(firestation.getStation());
				return oneFirestation;
			}
		}

		return null;
	}

	@Override
	public boolean deleteAFirestation(Firestation firestation) {

		boolean isRemoved = false;

		if (firestation != null) {
			for (Firestation oneFirestation : firestations) {

				if (oneFirestation.getStation().equalsIgnoreCase(firestation.getStation())
						&& oneFirestation.getAddress().equalsIgnoreCase(firestation.getAddress())) {

					isRemoved = firestations.remove(oneFirestation);
					break;
				}
			}
		}
		return isRemoved;
	}
}
