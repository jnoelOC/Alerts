package com.safetynet.alerts.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.FirestationDTO;
import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.IFirestationRepository;
import com.safetynet.alerts.repository.IMedicalRecordRepository;
import com.safetynet.alerts.repository.IPersonRepository;
import com.safetynet.alerts.utils.CalculateAge;
import com.safetynet.alerts.utils.FirestationMapper;
import com.safetynet.alerts.utils.MedicalRecordMapper;
import com.safetynet.alerts.utils.PersonInfo;
import com.safetynet.alerts.utils.PersonMapper;

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
	IMedicalRecordService medicalRecordService;

	private FirestationMapper firestationMapper = new FirestationMapper();
	private PersonMapper personMapper = new PersonMapper();
	private MedicalRecordMapper medicalrecordMapper = new MedicalRecordMapper();

	public List<FirestationDTO> getAllFirestations() {
		// utiliser Service !?!?!
		List<Firestation> listNotDto = firestationRepository.findAllFirestations();
		List<FirestationDTO> listFirestationDto = new ArrayList<>();

		for (Firestation firestation : listNotDto) {
			FirestationDTO firestationDTO = firestationMapper.toFirestationDTO(firestation);
			listFirestationDto.add(firestationDTO);
		}
		return listFirestationDto;
	}

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

	public FirestationDTO getOneFirestation(String station, String address) {

		Firestation firestation = this.firestationRepository.readAFirestation(station, address);
		return firestationMapper.toFirestationDTO(firestation);
	}

	public List<FirestationDTO> getSeveralFirestations(String station) {
		List<Firestation> listNotDto = this.firestationRepository.readSeveralFirestations(station);
		List<FirestationDTO> listFirestationDto = new ArrayList<>();

		for (Firestation firestation : listNotDto) {
			FirestationDTO firestationDTO = firestationMapper.toFirestationDTO(firestation);
			listFirestationDto.add(firestationDTO);
		}
		return listFirestationDto;
	}

	public FirestationDTO updateOneFirestation(FirestationDTO firestationDTO) {
		Firestation firestation = firestationMapper.toFirestation(firestationDTO);
		Firestation f = firestationRepository.updateAFirestation(firestation);
		return firestationMapper.toFirestationDTO(f);
	}

	public boolean deleteOneFirestation(FirestationDTO firestationDTO) {
		Firestation firestation = firestationMapper.toFirestation(firestationDTO);
		return firestationRepository.deleteAFirestation(firestation);
	}

	//////////////////////////////// ////////////////////////////////////
	// URL1 - List of persons (with nb of children and adults) covered
	// by corresponding station

	public List<PersonInfo> getPersonsWithBirthdatesFromFirestations(String stationNumber) {

		SortedMap<String, Person> personsWithBirthDates = new TreeMap<>();
		List<PersonInfo> listOfPersonsWithNbAdultsAndNbChildren = null;
		try {
			List<Firestation> listOfFirestations = retrieveStationsAssociatedWith(stationNumber);

			List<Person> listOfPersons = retrieveAllPersons();

			// Retrieve a list of persons by comparing address of persons with address of
			// stations
			List<Person> listOfPersonsWithSameAddressOfStation = new ArrayList<>();
			for (Firestation oneFirestation : listOfFirestations) {
				for (Person onePerson : listOfPersons) {

					if (onePerson.getAddress().equalsIgnoreCase(oneFirestation.getAddress())) {

						listOfPersonsWithSameAddressOfStation.add(onePerson);
					}
				}
			}

			List<MedicalRecord> listOfMedRec = retrieveAllMedicalRecords();

			// Retrieve a list of birth dates in medical records associated with persons
			for (MedicalRecord oneMedRec : listOfMedRec) {
				for (Person onePerson : listOfPersonsWithSameAddressOfStation) {
					if (oneMedRec.getFirstName().equalsIgnoreCase(onePerson.getFirstName())
							&& oneMedRec.getLastName().equalsIgnoreCase(onePerson.getLastName())) {

						personsWithBirthDates.put(oneMedRec.getBirthDate(), onePerson);
					}
				}
			}

			// Calculate number of children
			CalculateAge calculateAge = new CalculateAge();
			Integer nbOfChildren = 0;
			for (Map.Entry<String, Person> mapElement : personsWithBirthDates.entrySet()) {
				String keyBirthdate = mapElement.getKey();
				if (calculateAge.calculateAgeOfPerson(keyBirthdate) < 18) {
					nbOfChildren += 1;
				}
			}

			Integer nbTotalOfPerson = personsWithBirthDates.size();
			// Calculate number of adults
			Integer nbOfAdults = nbTotalOfPerson - nbOfChildren;

			// Retrieve persons in a list
			List<Person> listOfPersonsCoveredByFirestation = new ArrayList<>();
			for (Map.Entry<String, Person> mapElement : personsWithBirthDates.entrySet()) {
				String keyBirthdate = mapElement.getKey();
				listOfPersonsCoveredByFirestation.add(personsWithBirthDates.get(keyBirthdate));
			}

			// only firstName, lastName, address, phone and nbAdults and nbChildren
			PersonInfo personInfo = new PersonInfo("", "", "", "", 0, 0);
			listOfPersonsWithNbAdultsAndNbChildren = new ArrayList<>();
			// Keep person info
			listOfPersonsWithNbAdultsAndNbChildren = personInfo.setInfoUrl1(listOfPersonsCoveredByFirestation,
					nbOfAdults, nbOfChildren);

		} catch (Exception ex) {
			logger.error(MessageFormat.format("Error : {0}.", ex.getMessage()));
		}

		return listOfPersonsWithNbAdultsAndNbChildren;
	}

	private List<Firestation> retrieveStationsAssociatedWith(String stationNumber) {
		// Retrieve list of stations with the stationNumber
		List<FirestationDTO> listOfFirestationsDTO = getSeveralFirestations(stationNumber);
		List<Firestation> listOfFirestations = new ArrayList<>();
		for (FirestationDTO oneFirestationDTO : listOfFirestationsDTO) {
			listOfFirestations.add(firestationMapper.toFirestation(oneFirestationDTO));
		}
		return listOfFirestations;
	}

	private List<Person> retrieveAllPersons() {
		// Retrieve list of all persons
		List<PersonDTO> listOfPersonsDTO = personService.findAllPersons();
		List<Person> listOfPersons = new ArrayList<>();
		for (PersonDTO personDTO : listOfPersonsDTO) {
			listOfPersons.add(personMapper.toPerson(personDTO));
		}
		return listOfPersons;
	}

	private List<MedicalRecord> retrieveAllMedicalRecords() {
		// Retrieve list of all medical records
		List<MedicalRecordDTO> listOfMedRecDTO = medicalRecordService.getAllMedicalRecords();
		List<MedicalRecord> listOfMedRec = new ArrayList<>();
		for (MedicalRecordDTO oneMedRecDTO : listOfMedRecDTO) {
			listOfMedRec.add(medicalrecordMapper.toMedicalRecord(oneMedRecDTO));
		}
		return listOfMedRec;
	}

	// Dans le controlleur on utilise des DTO
	// Dans la couche service service on mapp les DTO du controlleur vers les
	// entities du repository
	// Dans service on mapp les entities du repository vers les DTO du controlleur
	// Dans repository on utilise des entities
}
