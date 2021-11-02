package com.safetynet.alerts.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.ChildInfoDTO;
import com.safetynet.alerts.dto.ChildrenInfoDTO;
import com.safetynet.alerts.dto.FirestationDTO;
import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.dto.PersonUrl4DTO;
import com.safetynet.alerts.dto.PersonUrl6DTO;
import com.safetynet.alerts.dto.PersonsInfoDTO;
import com.safetynet.alerts.dto.PersonsUrl4DTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.IMedicalRecordRepository;
import com.safetynet.alerts.repository.IPersonRepository;
import com.safetynet.alerts.utils.CalculateAge;
import com.safetynet.alerts.utils.FirestationMapper;
import com.safetynet.alerts.utils.MedicalRecordMapper;
import com.safetynet.alerts.utils.PersonMapper;

@Service
public class UrlService {

	@Autowired
	IPersonService personService;
	@Autowired
	IFirestationService firestationService;
	@Autowired
	IMedicalRecordService medicalRecordService;
	@Autowired
	IPersonRepository personRepository;
	@Autowired
	IMedicalRecordRepository medicalRecordRepository;

	PersonMapper personMapper = new PersonMapper();
	FirestationMapper firestationMapper = new FirestationMapper();
	MedicalRecordMapper medicalRecordMapper = new MedicalRecordMapper();

	public static final Logger logger = LogManager.getLogger(UrlService.class);

	public List<MedicalRecordDTO> findAllMedicalRecord() {
		List<MedicalRecordDTO> listOfAllMedRecDTO = new ArrayList<>();
		for (MedicalRecord oneMedRec : medicalRecordRepository.findAllMedicalRecords()) {
			listOfAllMedRecDTO.add(medicalRecordMapper.toMedicalRecordDTO(oneMedRec));
		}
		return listOfAllMedRecDTO;
	}

	public List<PersonDTO> retrieveAllPersonsFrom(String address) {
		// Retrieve list of all persons from address
		List<PersonDTO> listOfPersonsDTOinput = new ArrayList<>();
		List<PersonDTO> listOfPersonsDTOoutput = new ArrayList<>();

		for (Person onePerson : personRepository.findAllPersons()) {
			listOfPersonsDTOinput.add(personMapper.toPersonDTO(onePerson));
		}

		for (PersonDTO personDTO : listOfPersonsDTOinput) {
			if (personDTO.getAddress().equalsIgnoreCase(address)) {

				listOfPersonsDTOoutput.add(personDTO);
			}
		}
		return listOfPersonsDTOoutput;
	}

	// Retrieve a list of persons by comparing address of persons with address of
	// stations
	public List<Person> retrievePersonsWithSameStationAddress(List<Firestation> listOfFirestations,
			List<Person> listOfPersons) {
		List<Person> listOfPersonsWithSameAddressOfStation = new ArrayList<>();
		for (Firestation oneFirestation : listOfFirestations) {
			for (Person onePerson : listOfPersons) {
				if (onePerson.getAddress().equalsIgnoreCase(oneFirestation.getAddress())) {
					listOfPersonsWithSameAddressOfStation.add(onePerson);
				}
			}
		}
		return listOfPersonsWithSameAddressOfStation;
	}

	public Integer computeAge(String firstName, String lastName) {
		Integer age = 0;
		CalculateAge ca = new CalculateAge();

		for (MedicalRecordDTO medRecDTO : findAllMedicalRecord()) {

			if (medRecDTO.getFirstName().equals(firstName) && medRecDTO.getLastName().equals(lastName)) {

				age = ca.calculateAgeOfPerson(medRecDTO.getBirthDate());
				break;
			}
		}
		return age;
	}

	public MedicalRecordDTO fillMedicalRecordDTO(MedicalRecordDTO medRecDTO, String firstName, String lastName) {

		for (MedicalRecordDTO mrDTO : findAllMedicalRecord()) {

			if (mrDTO.getFirstName().equals(firstName) && mrDTO.getLastName().equals(lastName)) {

				medRecDTO.setFirstName(firstName);
				medRecDTO.setLastName(lastName);
				medRecDTO.setMedications(mrDTO.getMedications());
				medRecDTO.setAllergies(mrDTO.getAllergies());
				medRecDTO.setBirthDate(mrDTO.getBirthDate());
				break;
			}
		}
		return medRecDTO;
	}

	//////////////////////////////// ////////////////////////////////////
	// URL1 - List of persons (with nb of children and adults) covered
	// by corresponding station

	public PersonsInfoDTO getPersonsWithBirthdatesFromFirestations(String stationNumber) {

		PersonsInfoDTO psid = new PersonsInfoDTO();
		TreeMap<String, Person> personsWithBirthDates = new TreeMap<>();

		try {
			List<Firestation> listOfFirestations = retrieveStationsAssociatedWith(stationNumber);

			// Find all persons
			List<Person> listOfPersons = new ArrayList<>();
			for (PersonDTO onePersonDTO : personService.findAllPersons()) {
				listOfPersons.add(personMapper.toPerson(onePersonDTO));
			}

			// Retrieve a list of persons by comparing address of persons with address of
			// stations
			List<Person> listOfPersonsWithSameAddressOfStation = retrievePersonsWithSameStationAddress(
					listOfFirestations, listOfPersons);

			// Find all medical records
			List<MedicalRecord> listOfMedRec = new ArrayList<>();
			for (MedicalRecordDTO oneMedRecDTO : medicalRecordService.getAllMedicalRecords()) {
				listOfMedRec.add(medicalRecordMapper.toMedicalRecord(oneMedRecDTO));
			}

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

			// Retrieve persons in a list from sortedMap
			List<Person> listOfPersonsCoveredByFirestation = new ArrayList<>();
			for (Map.Entry<String, Person> mapElement : personsWithBirthDates.entrySet()) {
				String keyBirthdate = mapElement.getKey();
				listOfPersonsCoveredByFirestation.add(personsWithBirthDates.get(keyBirthdate));
			}

			List<PersonInfoDTO> lpid = transformFromPersonToPersonInfoDTO(listOfPersonsCoveredByFirestation);

			// Keep person info
			psid.setPersons(lpid);
			psid.setNumberOfAdults(nbOfAdults);
			psid.setNumberOfChildren(nbOfChildren);

		} catch (Exception ex) {
			logger.error(MessageFormat.format("Error : {0}.", ex.getMessage()));
		}

		return psid;
	}

	private List<PersonInfoDTO> transformFromPersonToPersonInfoDTO(List<Person> listOfPersonsCoveredByFirestation) {
		// Transform from Person to PersonInfoDTO :firstName, lastName, address, phone
		List<PersonInfoDTO> lpid = new ArrayList<>();

		for (Person onePerson : listOfPersonsCoveredByFirestation) {
			PersonInfoDTO pi = new PersonInfoDTO();
			pi.setFirstName(onePerson.getFirstName());
			pi.setLastName(onePerson.getLastName());
			pi.setAddress(onePerson.getAddress());
			pi.setPhone(onePerson.getPhone());
			lpid.add(pi);
		}
		return lpid;
	}

	private List<Firestation> retrieveStationsAssociatedWith(String stationNumber) {

		List<FirestationDTO> listOfFirestationsDTO = firestationService.getSeveralFirestations(stationNumber);
		List<Firestation> listOfFirestations = new ArrayList<>();
		for (FirestationDTO oneFirestationDTO : listOfFirestationsDTO) {
			listOfFirestations.add(firestationMapper.toFirestation(oneFirestationDTO));
		}
		return listOfFirestations;
	}

	///////////////////////////////////////////////////////////////////////
	// URL3
	// List of phones from residents associated with this corresponding station
	public Set<String> getPhonesFromFirestations(String stationNumber) {

		List<Firestation> listOfFirestations = retrieveStationsAssociatedWith(stationNumber);

		// Find all persons
		List<Person> listOfPersons = new ArrayList<>();
		for (PersonDTO onePersonDTO : personService.findAllPersons()) {
			listOfPersons.add(personMapper.toPerson(onePersonDTO));
		}

		List<Person> listOfPersonsWithSameAddressOfStation = retrievePersonsWithSameStationAddress(listOfFirestations,
				listOfPersons);

		Set<String> listOfPhones = new HashSet<>();
		for (Person onePerson : listOfPersonsWithSameAddressOfStation) {
			listOfPhones.add(onePerson.getPhone());
		}

		return listOfPhones;
	}

	///////////////////////////////////////////////////////////////////////
	// URL4
	// List of persons living in address associated with this station address
	public PersonsUrl4DTO getPersonsWithFirestationsFrom(String address) {

		PersonsUrl4DTO listOfPersons4DTO = new PersonsUrl4DTO();
		List<PersonUrl4DTO> lp4DTO = new ArrayList<>();

		List<PersonDTO> lpDto = retrieveAllPersonsFrom(address);
		List<FirestationDTO> lfDto = firestationService.getAllFirestations();
		Integer age = 0;

		// Fill one object of type PersonUrl4DTO by infos
		for (FirestationDTO fDto : lfDto) {
			for (PersonDTO pDto : lpDto) {
				if (fDto.getAddress().equals(pDto.getAddress())) {
					PersonUrl4DTO p4 = new PersonUrl4DTO();
					p4.setFirstName(pDto.getFirstName());
					p4.setLastName(pDto.getLastName());
					p4.setPhone(pDto.getPhone());
					age = computeAge(pDto.getFirstName(), pDto.getLastName());
					p4.setAge(age);
					MedicalRecordDTO medRecDTO = new MedicalRecordDTO();
					medRecDTO = fillMedicalRecordDTO(medRecDTO, pDto.getFirstName(), pDto.getLastName());
					p4.setMedRec(medicalRecordMapper.toMedicalRecord(medRecDTO));

					lp4DTO.add(p4);

					listOfPersons4DTO.setNumberOfFirestation(fDto.getStation());
					listOfPersons4DTO.setListOfPersons(lp4DTO);
				}
			}
		}

		return listOfPersons4DTO;
	}

	// URL2 - List of children living at this address with adults
	public ChildrenInfoDTO getChildrenFrom(String address) {
		ChildrenInfoDTO cid = new ChildrenInfoDTO();

		List<ChildInfoDTO> listOfChildInfoDTO = new ArrayList<>();
		List<PersonDTO> listOfAllPersonsDTO = null;
		List<MedicalRecord> listOfMedRec = new ArrayList<>();
		TreeMap<String, MedicalRecord> smOfPersonsWithBirthdate = new TreeMap<>();
		CalculateAge ca = new CalculateAge();

		listOfAllPersonsDTO = retrieveAllPersonsFrom(address);

		// retrieve birthdates and persons
		for (PersonDTO onePersonDTO : listOfAllPersonsDTO) {
			listOfMedRec.add(retrieveMedRec(onePersonDTO.getFirstName(), onePersonDTO.getLastName()));
		}

		// transform from list to treemap with birthdate
		for (MedicalRecord oneMedRec : listOfMedRec) {
			smOfPersonsWithBirthdate.put(oneMedRec.getBirthDate(), oneMedRec);
		}

		TreeMap<String, String> listOfAdultsNames = new TreeMap<>();
		// Fill age of children and fill a list of adults
		for (Map.Entry<String, MedicalRecord> mapElement : smOfPersonsWithBirthdate.entrySet()) {
			String keyBirthdate = mapElement.getKey();
			ChildInfoDTO childInfoDTO = new ChildInfoDTO();
			Integer age = ca.calculateAgeOfPerson(keyBirthdate);
			if (age < 18) {
				// Add infos of children in ChildInfo object
				childInfoDTO.setChildFirstname(smOfPersonsWithBirthdate.get(keyBirthdate).getFirstName());
				childInfoDTO.setChildLastname(smOfPersonsWithBirthdate.get(keyBirthdate).getLastName());
				childInfoDTO.setAgeOfChild(age);
				listOfChildInfoDTO.add(childInfoDTO);
			} else {
				listOfAdultsNames.put(smOfPersonsWithBirthdate.get(keyBirthdate).getFirstName(),
						smOfPersonsWithBirthdate.get(keyBirthdate).getLastName());
			}
		}

		// Add adults in each ChildInfo object
		for (ChildInfoDTO cid1 : listOfChildInfoDTO) {
			cid1.setListOfAdultsAtHome(retrieveAListOfPersonsFrom(listOfAdultsNames));
		}

		cid.setListOfChildren(listOfChildInfoDTO);

		return cid;
	}

	public List<Person> retrieveAListOfPersonsFrom(TreeMap<String, String> listOfPersons) {
		List<Person> listOfAdults = new ArrayList<>();

		for (Map.Entry<String, String> entry : listOfPersons.entrySet()) {

			Person person = null;
			person = personMapper.toPerson(personService.getOnePerson(entry.getKey(), entry.getValue()));
			listOfAdults.add(person);
		}
		return listOfAdults;
	}

	public MedicalRecord retrieveMedRec(String firstName, String lastName) {
		// Retrieve list of medical records
		List<MedicalRecordDTO> listOfMedRecDTO = findAllMedicalRecord();

		for (MedicalRecordDTO oneMedRecDTO : listOfMedRecDTO) {
			if (oneMedRecDTO.getFirstName().equalsIgnoreCase(firstName)
					&& oneMedRecDTO.getLastName().equalsIgnoreCase(lastName)) {

				return medicalRecordMapper.toMedicalRecord(oneMedRecDTO);
			}
		}
		return null;
	}

	// URL5
	// List of lastname, phone, age and medical record from each person with
	// same family's name gathered by address
	public List<PersonUrl4DTO> getFamilyWith(List<String> listOfFirestationNb) {

		List<PersonUrl4DTO> lp5 = new ArrayList<>();
		List<PersonDTO> lpDto;
		List<PersonDTO> lp = new ArrayList<>();
		List<String> listOfAddresses = new ArrayList<>();
		Integer age = 0;

		List<FirestationDTO> listOfAllF = new ArrayList<>();
		listOfAllF = firestationService.getAllFirestations();

		// list of address with list of firestation number
		for (String numb : listOfFirestationNb) {
			for (FirestationDTO f : listOfAllF) {
				if (f.getStation().equals(numb)) {
					listOfAddresses.add(f.getAddress());
				}
			}
		}

		// list of family's name with address
		for (String address : listOfAddresses) {
			lpDto = retrieveAllPersonsFrom(address);
			lpDto.forEach(element -> lp.add(element));
		}

		// fill persons with infos
		for (PersonDTO pDto : lp) {
			PersonUrl4DTO p4 = new PersonUrl4DTO();
			p4.setFirstName(pDto.getFirstName());
			p4.setLastName(pDto.getLastName());
			p4.setPhone(pDto.getPhone());
			age = computeAge(pDto.getFirstName(), pDto.getLastName());
			p4.setAge(age);
			MedicalRecordDTO medRecDTO = new MedicalRecordDTO();
			medRecDTO = fillMedicalRecordDTO(medRecDTO, pDto.getFirstName(), pDto.getLastName());
			p4.setMedRec(medicalRecordMapper.toMedicalRecord(medRecDTO));

			lp5.add(p4);
		}

		return lp5;
	}

	// list of persons with same family's name
	private List<PersonDTO> getPersonWithSameFamily(String lastName) {
		List<PersonDTO> lpDto = new ArrayList<>();
		for (PersonDTO onePersDto : personService.findAllPersons()) {
			if (onePersDto.getLastName().equalsIgnoreCase(lastName)) {
				lpDto.add(onePersDto);
			}
		}
		return lpDto;
	}

	// URL6
	// List of lastname, address, age, mail and medical record from each person
	public List<PersonUrl6DTO> getPersonWith(String firstName, String lastName) {

		List<PersonUrl6DTO> lp6 = new ArrayList<>();
		List<PersonDTO> lpDto = new ArrayList<>();

		lpDto = getPersonWithSameFamily(lastName);

		for (PersonDTO onePersDTO : lpDto) {
			if (onePersDTO.getLastName().equalsIgnoreCase(lastName)) {
				PersonUrl6DTO p6Dto = new PersonUrl6DTO();
				p6Dto.setLastName(onePersDTO.getLastName());
				p6Dto.setAddress(onePersDTO.getAddress());
				p6Dto.setAge(computeAge(onePersDTO.getFirstName(), onePersDTO.getLastName()));
				p6Dto.setMail(onePersDTO.getEmail());

				MedicalRecordDTO medRecDTO = new MedicalRecordDTO();
				medRecDTO = fillMedicalRecordDTO(medRecDTO, onePersDTO.getFirstName(), onePersDTO.getLastName());
				p6Dto.setMedRec(medicalRecordMapper.toMedicalRecord(medRecDTO));

				lp6.add(p6Dto);
			}
		}
		return lp6;
	}

	// URL7
	// list mails of each person from this city
	public List<String> getAllEmailsFrom(String city) {
		List<String> ls = null;

		ls = this.personRepository.getEmailsFrom(city);
		return ls;
	}

}
