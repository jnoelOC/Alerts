package com.safetynet.alerts.utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.safetynet.alerts.model.Person;

public class PersonInfo {

	public static final Logger logger = LogManager.getLogger(PersonInfo.class);

	String firstName;
	String lastName;
	String address;
	String phone;
	Integer nbOfAdults;
	Integer nbOfChildren;

	public PersonInfo(String firstNam, String lastNam, String addres, String phon, Integer nbAdults,
			Integer nbChildren) {
		// constructor
		firstName = firstNam;
		lastName = lastNam;
		address = addres;
		phone = phon;
		nbOfAdults = nbAdults;
		nbOfChildren = nbChildren;
	}

	public List<PersonInfo> setInfoUrl1(List<Person> listOfPersonsCoveredByFirestation, Integer nbAdults,
			Integer nbChildren) {
		List<PersonInfo> listOfPersonsWithNbAdultsAndNbChildren = new ArrayList<>();
		try {
			for (Person onePerson : listOfPersonsCoveredByFirestation) {
				PersonInfo pi = new PersonInfo("", "", "", "", 0, 0);
				pi.setFirstName(onePerson.getFirstName());
				pi.setLastName(onePerson.getLastName());
				pi.setAddress(onePerson.getAddress());
				pi.setPhone(onePerson.getPhone());
				pi.setNbOfAdults(nbAdults);
				pi.setNbOfChildren(nbChildren);
				listOfPersonsWithNbAdultsAndNbChildren.add(pi);
			}
		} catch (Exception ex) {

			logger.error(MessageFormat.format("Error in setInfo() : {0}.", ex.getMessage()));
		}
		return listOfPersonsWithNbAdultsAndNbChildren;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getNbOfAdults() {
		return nbOfAdults;
	}

	public void setNbOfAdults(Integer nbOfAdults) {
		this.nbOfAdults = nbOfAdults;
	}

	public Integer getNbOfChildren() {
		return nbOfChildren;
	}

	public void setNbOfChildren(Integer nbOfChildren) {
		this.nbOfChildren = nbOfChildren;
	}

}
