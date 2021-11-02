package com.safetynet.alerts.dto;

import com.safetynet.alerts.model.MedicalRecord;

public class PersonUrl6DTO {

	String lastName;
	String address;
	Integer age;
	String mail;
	MedicalRecord medRec;

	public PersonUrl6DTO() {

	}

	public PersonUrl6DTO(String lastName, String address, Integer age, String mail, MedicalRecord medRec) {

		this.lastName = lastName;
		this.address = address;
		this.age = age;
		this.mail = mail;
		this.medRec = medRec;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public MedicalRecord getMedRec() {
		return medRec;
	}

	public void setMedRec(MedicalRecord medRec) {
		this.medRec = medRec;
	}

}
