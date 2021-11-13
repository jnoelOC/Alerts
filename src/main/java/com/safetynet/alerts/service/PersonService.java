package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.IPersonRepository;
import com.safetynet.alerts.utils.PersonMapper;

@Service
public class PersonService implements IPersonService {

	public static final Logger logger = LogManager.getLogger(PersonService.class);

	@Autowired
	UrlService us;
	@Autowired
	IPersonRepository personRepository;

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

	public List<String> getAllEmailsFrom(String city) {
		if (city.isBlank()) {
			return Collections.emptyList();
		} else {
			return personRepository.getEmailsFrom(city);
		}
	}
}
