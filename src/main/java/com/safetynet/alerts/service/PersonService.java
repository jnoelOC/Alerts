package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.ChildInfoDTO;
import com.safetynet.alerts.dto.ChildrenInfoDTO;
import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.IPersonRepository;
import com.safetynet.alerts.utils.CalculateAge;
import com.safetynet.alerts.utils.MedicalRecordMapper;
import com.safetynet.alerts.utils.PersonMapper;

@Service
public class PersonService implements IPersonService {

	public static final Logger logger = LogManager.getLogger(PersonService.class);

	@Autowired
	UrlService us;
	@Autowired
	IPersonRepository personRepository;
	@Autowired
	IMedicalRecordService medicalRecordService;

	private MedicalRecordMapper medicalrecordMapper = new MedicalRecordMapper();
	private PersonMapper personMapper = new PersonMapper();

	public List<PersonDTO> findAllPersons() {
		List<Person> listePasDto = personRepository.findAllPersons();
		List<PersonDTO> listePersonDto = new ArrayList<>();

		for (Person person : listePasDto) {
			PersonDTO personDTO = personMapper.toPersonDTO(person);
			listePersonDto.add(personDTO);
		}

		return listePersonDto;
	}

	public PersonDTO addPerson(PersonDTO personDTO) {
		PersonDTO pDTO;
		Person person = personMapper.toPerson(personDTO);
		Person p = this.personRepository.save(person);

		if (p == null) {
			pDTO = null;
		} else {
			pDTO = personMapper.toPersonDTO(p);
		}
		return pDTO;
	}

	public PersonDTO getOnePerson(String firstName, String lastName) {
		Person person = this.personRepository.readAPerson(firstName, lastName);
		return personMapper.toPersonDTO(person);
	}

	public PersonDTO updateOnePerson(PersonDTO personDTO) {
		Person person = personMapper.toPerson(personDTO);
		Person p = personRepository.updateAPerson(person);
		return personMapper.toPersonDTO(p);
	}

	public boolean deleteOnePerson(PersonDTO personDTO) {
		Person person = personMapper.toPerson(personDTO);
		return personRepository.deleteAPerson(person);
	}

	// URL7
	public List<String> getAllEmailsFrom(String city) {
		List<String> ls = null;

		ls = this.personRepository.getEmailsFrom(city);
		return ls;
	}

	// URL2 - List of children living at this address with adults
	public ChildrenInfoDTO getChildrenFrom(String address) {
		ChildrenInfoDTO cid = new ChildrenInfoDTO();

		List<ChildInfoDTO> listOfChildInfoDTO = new ArrayList<>();
		List<PersonDTO> listOfAllPersonsDTO = null;
		List<MedicalRecord> listOfMedRec = new ArrayList<>();
		TreeMap<String, MedicalRecord> smOfPersonsWithBirthdate = new TreeMap<>();
		CalculateAge ca = new CalculateAge();

		listOfAllPersonsDTO = us.retrieveAllPersonsFrom(address);

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
				// Ajout des infos children dans l'objet ChildInfo
				childInfoDTO.setChildFirstname(smOfPersonsWithBirthdate.get(keyBirthdate).getFirstName());
				childInfoDTO.setChildLastname(smOfPersonsWithBirthdate.get(keyBirthdate).getLastName());
				childInfoDTO.setAgeOfChild(age);
				listOfChildInfoDTO.add(childInfoDTO);
			} else {
				listOfAdultsNames.put(smOfPersonsWithBirthdate.get(keyBirthdate).getFirstName(),
						smOfPersonsWithBirthdate.get(keyBirthdate).getLastName());
			}
		}

		// Ajout des adultes dans chaque objet ChildInfo
		for (ChildInfoDTO cid1 : listOfChildInfoDTO) {
			cid1.setListOfAdultsAtHome(retrieveAListOfPersonsFrom(listOfAdultsNames));
		}

		cid.setListOfChildren(listOfChildInfoDTO);

		return cid;
	}

	public List<Person> retrieveAListOfPersonsFrom(TreeMap<String, String> listOfPersons) {
		List<Person> listOfAdults = new ArrayList<>();

		for (Map.Entry<String, String> entry : listOfPersons.entrySet()) {
			Person person = new Person();
			person.setFirstName(entry.getKey());
			person.setLastName(entry.getValue());
			listOfAdults.add(person);
		}
		return listOfAdults;
	}

	public MedicalRecord retrieveMedRec(String firstName, String lastName) {
		// Retrieve list of medical records
		List<MedicalRecordDTO> listOfMedRecDTO = us.findAllMedicalRecord();

		for (MedicalRecordDTO oneMedRecDTO : listOfMedRecDTO) {
			if (oneMedRecDTO.getFirstName().equalsIgnoreCase(firstName)
					&& oneMedRecDTO.getLastName().equalsIgnoreCase(lastName)) {

				return medicalrecordMapper.toMedicalRecord(oneMedRecDTO);
			}
		}
		return null;
	}
}
