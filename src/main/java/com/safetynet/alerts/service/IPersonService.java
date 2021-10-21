package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.dto.ChildrenInfoDTO;
import com.safetynet.alerts.dto.PersonDTO;

public interface IPersonService {

	public PersonDTO addPerson(PersonDTO personDTO);

	public List<PersonDTO> findAllPersons();

	public PersonDTO getOnePerson(String firstName, String lastName);

	public PersonDTO updateOnePerson(PersonDTO personDTO);

	public boolean deleteOnePerson(PersonDTO personDTO);

	// URL7
	public List<String> getAllEmailsFrom(String city);

	// URL2
	public ChildrenInfoDTO getChildrenFrom(String address);
}
