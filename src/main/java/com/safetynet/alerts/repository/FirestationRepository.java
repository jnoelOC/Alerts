package com.safetynet.alerts.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Firestation;

@Repository
public class FirestationRepository implements IFirestationRepository {

	private List<Firestation> firestations = new ArrayList<Firestation>();

	@Override
	public Firestation save(Firestation firestation) {
		firestations.add(firestation);
		return firestation;
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
		List<Firestation> fs = new ArrayList<Firestation>();

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

				if (firestations.get(numFirestation).getStation().equals(firestation.getStation())
						&& firestations.get(numFirestation).getAddress().equalsIgnoreCase(firestation.getAddress())) {

					// firestations.get(numFirestation).setAddress("1101 Montreal street");
					firestations.get(numFirestation).setStation("9");

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
	public void deleteAFirestation(Firestation firestation) {
		Iterator<Firestation> iteratorFirestations = firestations.iterator();

		try {
			Integer numFirestation = 0;
			while (iteratorFirestations.hasNext()) {

				if (firestations.get(numFirestation).getStation().equalsIgnoreCase(firestation.getStation())
						&& firestations.get(numFirestation).getAddress().equalsIgnoreCase(firestation.getAddress())) {

					firestations.remove(firestations.get(numFirestation));

				} else {
					numFirestation++;
					iteratorFirestations.next();
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
