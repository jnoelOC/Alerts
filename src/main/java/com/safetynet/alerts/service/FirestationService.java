package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.IFirestationRepository;
import com.safetynet.alerts.repository.IMedicalRecordRepository;
import com.safetynet.alerts.repository.IPersonRepository;

@Service
public class FirestationService implements IFirestationService {
	@Autowired
	IFirestationRepository firestationRepository;
	@Autowired
	IPersonRepository personRepository;
	@Autowired
	IMedicalRecordRepository medicalrecordRepository;

	@Override
	public List<Firestation> getAllFirestations() {
		return firestationRepository.findAllFirestations();
	}

	@Override
	public Firestation addFirestation(Firestation firestation) {
		return this.firestationRepository.save(firestation);
	}

	@Override
	public Firestation getOneFirestation(String station, String address) {
		return this.firestationRepository.readAFirestation(station, address);
	}

	@Override
	public List<Firestation> getSeveralFirestations(String Station) {
		return this.firestationRepository.readSeveralFirestations(Station);
	}

	@Override
	public Firestation updateOneFirestation(Firestation firestation) {
		return firestationRepository.updateAFirestation(firestation);
	}

	@Override
	public void deleteOneFirestation(Firestation firestation) {
		firestationRepository.deleteAFirestation(firestation);
	}

	// List of persons covered by corresponding station
	@Override
	public TreeMap<String, Person> getPersonsFromFirestations(String stationNumber) {

		List<Person> resultingLp = new ArrayList<>();

		// Retrieve list of stations with the stationNumber
		List<Firestation> lf = getSeveralFirestations(stationNumber);

		// Retrieve list of all persons
		List<Person> lp = personRepository.findAllPersons(); // utiliser service !!!

		// Retrieve a list of persons by comparing address of persons with address of
		// stations
		for (int numPerson = 0; numPerson < lp.size(); numPerson++) {
			for (int numFirestation = 0; numFirestation < lf.size(); numFirestation++) {
				if (lp.get(numPerson).getAddress().equalsIgnoreCase(lf.get(numFirestation).getAddress())) {

					resultingLp.add(lp.get(numPerson));
					break;
				}
			}
		}

		TreeMap<String, Person> personsWithBirthDates = new TreeMap<String, Person>();

		// Retrieve list of all medicalrecords
		List<MedicalRecord> lmr = medicalrecordRepository.findAllMedicalRecords();

		// Retrieve a list of birthdate in medicalrecords with persons
		// for(MedicalRecord mr : lmr) {

		// }
		for (int numMedicRec = 0; numMedicRec < lmr.size(); numMedicRec++) {
			for (int numPerson = 0; numPerson < resultingLp.size(); numPerson++) {
				if (lmr.get(numMedicRec).getFirstName().equalsIgnoreCase(resultingLp.get(numPerson).getFirstName())
						&& lmr.get(numMedicRec).getLastName()
								.equalsIgnoreCase(resultingLp.get(numPerson).getLastName())) {

					personsWithBirthDates.put(lmr.get(numMedicRec).getBirthDate(), resultingLp.get(numPerson));
				}
			}
		}

		// Calculate number of adults and number of children

		// only firstName, lastName, address, phone

		return personsWithBirthDates;
	}
}
