package com.safetynet.alerts.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Firestation;

@Repository
public class FirestationRepository implements IFirestationRepository {

	public static final Logger logger = LogManager.getLogger(FirestationRepository.class);

	private List<Firestation> firestations = new ArrayList<Firestation>();

	@Override
	public Firestation save(Firestation firestation) {

		Iterator<Firestation> iteratorFirestations = firestations.iterator();

		try {
			boolean fFound = false;
			Integer numFirestation = 0;
			while (iteratorFirestations.hasNext()) {

				if (firestations.get(numFirestation).getAddress().equalsIgnoreCase(firestation.getAddress())) {

					fFound = true;
					break;
				}

				numFirestation++;
				iteratorFirestations.next();
			}

			if (!fFound) {
				firestations.add(firestation);
				return firestation;
			} else { // ALREADY CREATED
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

	@Override
	public List<Firestation> findAllFirestations() {
		return firestations;
	}

	@Override
	public Firestation readAFirestation(String Station, String Address) {

		Iterator<Firestation> iteratorFirestations = firestations.iterator();

		try {
			Integer numFirestation = 0;
			while (iteratorFirestations.hasNext()) {

				if (firestations.get(numFirestation).getStation().equals(Station)
						&& firestations.get(numFirestation).getAddress().equalsIgnoreCase(Address)) {

					return firestations.get(numFirestation);
				}

				numFirestation++;
				iteratorFirestations.next();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

	@Override
	public List<Firestation> readSeveralFirestations(String Station) {

		Iterator<Firestation> iteratorFirestations = firestations.iterator();
		List<Firestation> fs = new ArrayList<>();

		try {
			Integer numFirestation = 0;
			while (iteratorFirestations.hasNext()) {

				if (firestations.get(numFirestation).getStation().equals(Station)) {

					fs.add(firestations.get(numFirestation));
				}

				numFirestation++;
				iteratorFirestations.next();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return fs;
	}

	@Override
	public Firestation updateAFirestation(Firestation firestation) {
		Iterator<Firestation> iteratorFirestations = firestations.iterator();

		try {
			Integer numFirestation = 0;
			while (iteratorFirestations.hasNext()) {

				if (firestations.get(numFirestation).getAddress().equalsIgnoreCase(firestation.getAddress())) {

					firestations.get(numFirestation).setStation(firestation.getStation());
					return firestations.get(numFirestation);
				}

				numFirestation++;
				iteratorFirestations.next();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteAFirestation(Firestation firestation) {
		Iterator<Firestation> iteratorFirestations = firestations.iterator();
		boolean isRemoved = false;
		try {
			Integer numFirestation = 0;
			while (iteratorFirestations.hasNext()) {

				if (firestations.get(numFirestation).getStation().equalsIgnoreCase(firestation.getStation())
						&& firestations.get(numFirestation).getAddress().equalsIgnoreCase(firestation.getAddress())) {

					firestations.remove(firestations.get(numFirestation));
					isRemoved = true;
					break;
				} else {
					numFirestation++;
					iteratorFirestations.next();
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isRemoved;
	}
}
