package com.safetynet.alerts.dto;

import java.util.ArrayList;
import java.util.List;

import com.safetynet.alerts.model.Person;

public class ChildInfoDTO {
	String childFirstname;
	String childLastname;
	Integer ageOfChild;
	List<Person> listOfAdultsAtHome = new ArrayList<>();

	public ChildInfoDTO() {
		// constructor
	}

	public ChildInfoDTO(String childFirstname, String childLastname, Integer ageOfChild,
			List<Person> listOfAdultsAtHome) {

		this.childFirstname = childFirstname;
		this.childLastname = childLastname;
		this.ageOfChild = ageOfChild;
		this.listOfAdultsAtHome = listOfAdultsAtHome;
	}

	public String getChildFirstname() {
		return childFirstname;
	}

	public void setChildFirstname(String childFirstname) {
		this.childFirstname = childFirstname;
	}

	public String getChildLastname() {
		return childLastname;
	}

	public void setChildLastname(String childLastname) {
		this.childLastname = childLastname;
	}

	public Integer getAgeOfChild() {
		return ageOfChild;
	}

	public void setAgeOfChild(Integer ageOfChild) {
		this.ageOfChild = ageOfChild;
	}

	public List<Person> getListOfAdultsAtHome() {
		return listOfAdultsAtHome;
	}

	public void setListOfAdultsAtHome(List<Person> listOfAdultsAtHome) {
		this.listOfAdultsAtHome = listOfAdultsAtHome;
	}

}
