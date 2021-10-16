package com.safetynet.alerts.model;

public class PersonInfo {

	String firstName;
	String lastName;
	String address;
	String phone;

	public PersonInfo() {
		// constructor
	}

	public PersonInfo(String firstNam, String lastNam, String addres, String phon) {
		firstName = firstNam;
		lastName = lastNam;
		address = addres;
		phone = phon;
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
}
