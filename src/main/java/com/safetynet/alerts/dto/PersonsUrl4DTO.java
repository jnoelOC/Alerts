package com.safetynet.alerts.dto;

import java.util.List;

public class PersonsUrl4DTO {

	List<PersonUrl4DTO> listOfPersons;
	String numberOfFirestation;

	public PersonsUrl4DTO() {

	}

	public PersonsUrl4DTO(List<PersonUrl4DTO> listOfPersons, String numberOfFirestation) {
		this.listOfPersons = listOfPersons;
		this.numberOfFirestation = numberOfFirestation;
	}

	public List<PersonUrl4DTO> getListOfPersons() {
		return listOfPersons;
	}

	public void setListOfPersons(List<PersonUrl4DTO> listOfPersons) {
		this.listOfPersons = listOfPersons;
	}

	public String getNumberOfFirestation() {
		return numberOfFirestation;
	}

	public void setNumberOfFirestation(String numberOfFirestation) {
		this.numberOfFirestation = numberOfFirestation;
	}

}
