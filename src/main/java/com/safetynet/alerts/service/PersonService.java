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

	@Override
	public Person addPerson(PersonDTO personDTO) {
		Person person = personMapper.toPerson(personDTO);
		return this.personRepository.save(person);
	}

	@Override
	public PersonDTO getOnePerson(String firstName, String lastName) {
		Person person = this.personRepository.readAPerson(firstName, lastName);
		PersonDTO personDTO = personMapper.toPersonDTO(person);
		return personDTO;
	}

	@Override
	public Person updateOnePerson(PersonDTO personDTO) {
		Person person = personMapper.toPerson(personDTO);
		return personRepository.updateAPerson(person);
	}

	@Override
	public boolean deleteOnePerson(PersonDTO personDTO) {
		Person person = personMapper.toPerson(personDTO);
		return personRepository.deleteAPerson(person);
	}

}
