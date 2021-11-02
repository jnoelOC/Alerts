package com.safetynet.alerts.dto;

import com.safetynet.alerts.model.MedicalRecord;

public class PersonUrl4DTO {

	String firstName;
	String lastName;
	String phone;
	Integer age;
	MedicalRecord medRec;

	public PersonUrl4DTO() {

	}

	public PersonUrl4DTO(String firstName, String lastName, String phone, Integer age, MedicalRecord medRec) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.age = age;
		this.medRec = medRec;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public MedicalRecord getMedRec() {
		return medRec;
	}

	public void setMedRec(MedicalRecord medRec) {
		this.medRec = medRec;
	}

}
