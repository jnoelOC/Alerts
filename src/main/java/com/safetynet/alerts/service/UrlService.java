package com.safetynet.alerts.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
import com.safetynet.alerts.utils.CalculateAge;
import com.safetynet.alerts.utils.FirestationMapper;
import com.safetynet.alerts.utils.MedicalRecordMapper;
import com.safetynet.alerts.utils.PersonMapper;

@Service
public class UrlService implements IUrlService {

	@Autowired
	IPersonService personService;
	@Autowired
	IFirestationService firestationService;
	@Autowired
	IMedicalRecordService medicalRecordService;

	PersonMapper personMapper = new PersonMapper();
	FirestationMapper firestationMapper = new FirestationMapper();
	MedicalRecordMapper medicalRecordMapper = new MedicalRecordMapper();

	public static final Logger logger = LogManager.getLogger(UrlService.class);

	/*
	 * Find all medical records
	 * 
	 * @return List<MedicalRecordDTO>
	 * 
	 */
	public List<MedicalRecordDTO> findAllMedicalRecord() {
		return medicalRecordService.getAllMedicalRecords().stream().collect(Collectors.toList());
	}

	/*
	 * Retrieve all persons associated with an address
	 * 
	 * @param String address
	 * 
	 * @return List<PersonDTO>
	 * 
	 */
	public List<PersonDTO> retrieveAllPersonsFrom(String address) {
		return personService.findAllPersons().stream().filter(person -> person.getAddress().equalsIgnoreCase(address))
				.collect(Collectors.toList());
	}

	/*
	 * Retrieve a list of persons by comparing address of persons with address of
	 * stations
	 * 
	 * @param List<Firestation> listOfFirestations and List<Person> listOfPersons
	 * 
	 * @return List<Person>
	 * 
	 */
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

	/*
	 * Calculate age of a person with its firstname and lastname
	 * 
	 * @param String firstName and String lastName
	 * 
	 * @return Integer age
	 * 
	 */
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

	/*
	 * Fill medical record DTO with data
	 * 
	 * @param MedicalRecordDTO medRecDTO and String firstName and String lastName
	 * 
	 * @return MedicalRecordDTO object
	 * 
	 */
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

	/*
	 * URL1 List of persons (with nb of children and adults) covered by
	 * corresponding station
	 * 
	 * @param String stationNumber
	 * 
	 * @return PersonsInfoDTO object
	 * 
	 */
	public PersonsInfoDTO getPersonsWithBirthdatesFromFirestations(String stationNumber) {

		PersonsInfoDTO psid = new PersonsInfoDTO();
		TreeMap<String, Person> personsWithBirthDates = new TreeMap<>();

		try {
			List<Firestation> listOfFirestations = retrieveStationsAssociatedWith(stationNumber);

			// Find all persons
			List<Person> listOfPersons = personService.findAllPersons().stream()
					.map(onePersonDTO -> personMapper.toPerson(onePersonDTO)).collect(Collectors.toList());

			// Retrieve a list of persons by comparing address of persons with address of
			// stations
			List<Person> listOfPersonsWithSameAddressOfStation = retrievePersonsWithSameStationAddress(
					listOfFirestations, listOfPersons);

			// Find all medical records
			List<MedicalRecord> listOfMedRec = medicalRecordService.getAllMedicalRecords().stream()
					.map(oneMedRecDTO -> medicalRecordMapper.toMedicalRecord(oneMedRecDTO))
					.collect(Collectors.toList());

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

	/*
	 * Transform from a person to personInfoDTO
	 * 
	 * @param List<Person> listOfPersonsCoveredByFirestation
	 * 
	 * @return List<PersonInfoDTO>
	 * 
	 */
	private List<PersonInfoDTO> transformFromPersonToPersonInfoDTO(List<Person> listOfPersonsCoveredByFirestation) {
		// Transform from Person to PersonInfoDTO :firstName, lastName, address, phone
		return listOfPersonsCoveredByFirestation.stream().map(onePerson -> {
			PersonInfoDTO pi = new PersonInfoDTO();
			pi.setFirstName(onePerson.getFirstName());
			pi.setLastName(onePerson.getLastName());
			pi.setAddress(onePerson.getAddress());
			pi.setPhone(onePerson.getPhone());
			return pi;
		}).collect(Collectors.toList());

	}

	/*
	 * Retrieve a station from a stationNumber
	 * 
	 * @param String stationNumber
	 * 
	 * @return List<Firestation>
	 * 
	 */
	private List<Firestation> retrieveStationsAssociatedWith(String stationNumber) {
		return firestationService.getSeveralFirestations(stationNumber).stream()
				.map(oneFirestationDTO -> firestationMapper.toFirestation(oneFirestationDTO))
				.collect(Collectors.toList());
	}

	/*
	 * URL3 get List of phones from residents associated with this corresponding
	 * station
	 * 
	 * @param String stationNumber
	 * 
	 * @return Set<String>
	 * 
	 */
	public Set<String> getPhonesFromFirestations(String stationNumber) {

		List<Firestation> listOfFirestations = retrieveStationsAssociatedWith(stationNumber);

		// Find all persons
		List<Person> listOfPersons = personService.findAllPersons().stream()
				.map(onePersonDTO -> personMapper.toPerson(onePersonDTO)).collect(Collectors.toList());

		List<Person> listOfPersonsWithSameAddressOfStation = retrievePersonsWithSameStationAddress(listOfFirestations,
				listOfPersons);

		return listOfPersonsWithSameAddressOfStation.stream().map(onePerson -> onePerson.getPhone())
				.collect(Collectors.toSet());
	}

	/*
	 * URL4 List of persons living in address associated with this station address
	 * 
	 * @param String address
	 * 
	 * @return PersonsUrl4DTO object
	 * 
	 */
	public PersonsUrl4DTO getPersonsWithFirestationsFrom(String address) {

		PersonsUrl4DTO listOfPersons4DTO = new PersonsUrl4DTO();
		List<PersonUrl4DTO> lp4DTO = new ArrayList<>();

		List<PersonDTO> lpDto = retrieveAllPersonsFrom(address);
		List<FirestationDTO> lfDto = firestationService.getAllFirestations();
		Integer age = 0;

		if (lpDto.isEmpty()) {
			listOfPersons4DTO.setListOfPersons(null);
			listOfPersons4DTO.setNumberOfFirestation(null);
			return listOfPersons4DTO;
		}

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

	/*
	 * URL2 List of children living at this address with adults
	 * 
	 * @param String address
	 * 
	 * @return ChildrenInfoDTO object
	 */
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
		for (ChildInfoDTO cidDto : listOfChildInfoDTO) {
			cidDto.setListOfAdultsAtHome(retrieveAListOfPersonsFrom(listOfAdultsNames));
		}

		cid.setListOfChildren(listOfChildInfoDTO);

		return cid;
	}

	/*
	 * Retrieve a list of persons from a TreeMap listOfPersons
	 * 
	 * @param TreeMap<String, String> listOfPersons
	 * 
	 * @return List<Person>
	 * 
	 */
	public List<Person> retrieveAListOfPersonsFrom(TreeMap<String, String> listOfPersons) {
		List<Person> listOfAdults = new ArrayList<>();

		for (Map.Entry<String, String> entry : listOfPersons.entrySet()) {
			Person person = null;
			person = personMapper.toPerson(personService.getOnePerson(entry.getKey(), entry.getValue()));
			listOfAdults.add(person);
		}
		return listOfAdults;
	}

	/*
	 * Retrieve a medical record from firstname and lastname
	 * 
	 * @param String firstName, String lastName
	 * 
	 * @return MedicalRecord object
	 * 
	 */
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

	/*
	 * URL5 List of lastname, phone, age and medical record from each person with
	 * same family's name gathered by address
	 * 
	 * @param List<String> listOfFirestationNb
	 * 
	 * @return List<PersonUrl4DTO>
	 * 
	 */
	public List<PersonUrl4DTO> getFamilyWith(List<String> listOfFirestationNb) {

		List<PersonUrl4DTO> lp5 = new ArrayList<>();
		List<PersonDTO> lpDto;
		List<PersonDTO> lp = new ArrayList<>();
		List<String> listOfAddresses = new ArrayList<>();
		Integer age = 0;

		List<FirestationDTO> listOfAllF = firestationService.getAllFirestations();

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

	/*
	 * URL6 List of lastname, address, age, mail and medical record from each person
	 * 
	 * @param String firstName, String lastName
	 * 
	 * @return List<PersonUrl6DTO>
	 * 
	 */
	public List<PersonUrl6DTO> getPersonWith(String firstName, String lastName) {

		List<PersonUrl6DTO> lp6 = new ArrayList<>();
		List<PersonDTO> lpDto;

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

	/*
	 * list of persons with same family's name
	 * 
	 * @param String lastName
	 * 
	 * @return List<PersonDTO>
	 */
	private List<PersonDTO> getPersonWithSameFamily(String lastName) {

		return personService.findAllPersons().stream()
				.filter(personDto -> personDto.getLastName().equalsIgnoreCase(lastName)).collect(Collectors.toList());
	}

	/*
	 * URL7 List mails of each person from this city
	 * 
	 * @param String city
	 * 
	 * @return List<String>
	 * 
	 */
	public List<String> getAllEmailsFrom(String city) {
		List<String> ls = null;

		ls = personService.getAllEmailsFrom(city);
		return ls;
	}

}
