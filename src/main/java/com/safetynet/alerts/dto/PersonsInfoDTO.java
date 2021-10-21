package com.safetynet.alerts.dto;

import java.util.List;

public class PersonsInfoDTO {

	private List<PersonInfoDTO> persons;
	private Integer numberOfChildren;
	private Integer numberOfAdults;

	public PersonsInfoDTO() {
	}

	public PersonsInfoDTO(List<PersonInfoDTO> persons, Integer numberOfChildren, Integer numberOfAdults) {
		this.persons = persons;
		this.numberOfChildren = numberOfChildren;
		this.numberOfAdults = numberOfAdults;
	}

	public List<PersonInfoDTO> getPersons() {
		return persons;
	}

	public void setPersons(List<PersonInfoDTO> persons) {
		this.persons = persons;
	}

	public Integer getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(Integer numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public Integer getNumberOfAdults() {
		return numberOfAdults;
	}

	public void setNumberOfAdults(Integer numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}

}
