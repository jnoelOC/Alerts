package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.dto.PersonDTO;

public interface IPersonService {

	public PersonDTO addPerson(PersonDTO personDTO);

	public List<PersonDTO> findAllPersons();

	public PersonDTO getOnePerson(String firstName, String lastName);

	public PersonDTO updateOnePerson(PersonDTO personDTO);

	public Boolean deleteOnePerson(PersonDTO personDTO);

	public List<String> getAllEmailsFrom(String city);
}
