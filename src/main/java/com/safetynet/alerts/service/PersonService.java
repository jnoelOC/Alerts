package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.IPersonRepository;
import com.safetynet.alerts.utils.PersonMapper;

@Service
public class PersonService implements IPersonService {

	@Autowired
	IPersonRepository personRepository;

	private PersonMapper personMapper = new PersonMapper();

//	@Override
//	public List<Person> findAllPersons() {
//		return personRepository.findAllPersons();
//	}

	@Override
	public List<PersonDTO> findAllPersons() {
		List<Person> listePasDto = personRepository.findAllPersons();
		List<PersonDTO> listePersonDto = new ArrayList<PersonDTO>();
		for (Person person : listePasDto) {
			PersonDTO personDTO = personMapper.toPersonDTO(person);
			listePersonDto.add(personDTO);
		}
		return listePersonDto;
	}

//	@Override
//	public void addPerson(Person person) {
//		this.personRepository.save(person);
//	}

	@Override
	public void addPerson(PersonDTO personDTO) {
		Person person = personMapper.toPerson(personDTO);
		this.personRepository.save(person);
	}

	@Override
	public Person getOnePerson(String firstName, String lastName) {
		return this.personRepository.readAPerson(firstName, lastName);
	}

	@Override
	public void updateOnePerson(PersonDTO personDTO) {
		Person person = personMapper.toPerson(personDTO);
		personRepository.updateAPerson(person);
	}

	@Override
	public void deleteOnePerson(Person person) {
		personRepository.deleteAPerson(person);
	}
}
