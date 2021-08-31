package com.safetynet.alerts.model;

import java.util.List;

public class MedicalRecords {
	private List<MedicalRecord> medicalrecords;

	public MedicalRecords() {
	}

	public MedicalRecords(List<MedicalRecord> medicalrecords) {
		this.medicalrecords = medicalrecords;
	}

	public List<MedicalRecord> getMedicalrecords() {
		return medicalrecords;
	}

	public void setMedicalrecords(List<MedicalRecord> medicalrecords) {
		this.medicalrecords = medicalrecords;
	}

}
