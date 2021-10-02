package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.FirestationDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.IFirestationRepository;
import com.safetynet.alerts.repository.IMedicalRecordRepository;
import com.safetynet.alerts.repository.IPersonRepository;
import com.safetynet.alerts.utils.FirestationMapper;

@Service
public class FirestationService implements IFirestationService {

	public static final Logger logger = LogManager.getLogger(FirestationService.class);

	@Autowired
	IFirestationRepository firestationRepository;
	@Autowired
	IPersonRepository personRepository;
	@Autowired
	IMedicalRecordRepository medicalrecordRepository;

	@Autowired
	IPersonService personService;
	@Autowired
	IFirestationService firestationService;

	private FirestationMapper firestationMapper = new FirestationMapper();

	@Override
	public List<FirestationDTO> getAllFirestations() {
		List<Firestation> listNotDto = firestationRepository.findAllFirestations();
		List<FirestationDTO> listFirestationDto = new ArrayList<>();

		for (Firestation firestation : listNotDto) {
			FirestationDTO firestationDTO = firestationMapper.toFirestationDTO(firestation);
			listFirestationDto.add(firestationDTO);
		}
		return listFirestationDto;
	}

	@Override
	public FirestationDTO addFirestation(FirestationDTO firestationDTO) {
		FirestationDTO fDTO;
		Firestation firestation = firestationMapper.toFirestation(firestationDTO);
		Firestation f = this.firestationRepository.save(firestation);

		if (f == null) {
			fDTO = null;
		} else {
			fDTO = firestationMapper.toFirestationDTO(f);
		}
		return fDTO;
	}

	@Override
	public FirestationDTO getOneFirestation(String station, String address) {

		Firestation firestation = this.firestationRepository.readAFirestation(station, address);
		return firestationMapper.toFirestationDTO(firestation);
	}

	@Override
	public List<FirestationDTO> getSeveralFirestations(String station) {
		List<Firestation> listNotDto = this.firestationRepository.readSeveralFirestations(station);
		List<FirestationDTO> listFirestationDto = new ArrayList<>();

		for (Firestation firestation : listNotDto) {
			FirestationDTO firestationDTO = firestationMapper.toFirestationDTO(firestation);
			listFirestationDto.add(firestationDTO);
		}
		return listFirestationDto;
	}

	@Override
	public FirestationDTO updateOneFirestation(FirestationDTO firestationDTO) {
		Firestation firestation = firestationMapper.toFirestation(firestationDTO);
		Firestation f = firestationRepository.updateAFirestation(firestation);
		return firestationMapper.toFirestationDTO(f);
	}

	@Override
	public boolean deleteOneFirestation(FirestationDTO firestationDTO) {
		Firestation firestation = firestationMapper.toFirestation(firestationDTO);
		return firestationRepository.deleteAFirestation(firestation);
	}

	//////////////////////////////// A REVOIR COMPLETEMENT
	//////////////////////////////// ////////////////////////////////////
	// List of persons covered by corresponding station
	@Override
	public TreeMap<String, Person> getPersonsFromFirestations(String stationNumber) {

		List<Person> resultingLp = new ArrayList<>();

		// Retrieve list of stations with the stationNumber
		List<FirestationDTO> lf = getSeveralFirestations(stationNumber);

		// Retrieve list of all persons
		List<Person> lp = personRepository.findAllPersons(); // utiliser service !!!
//		List<Person> lp2 = personService.findAllPersons();

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
	// Dans le controlleur on utilise des DTO
	// Dans la couche service service on mapp les DTO du controlleur vers les
	// entities du repository
	// Dans service on mapp les entities du repository vers les DTO du controlleur
	// Dans repository on utilise des entities
}
