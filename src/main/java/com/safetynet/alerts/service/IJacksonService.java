package com.safetynet.alerts.service;

public interface IJacksonService {

	// 1) on a besoin de récupérer de la données
	public void getData();

	// 2) on a besoin d'initialiser la liste des persons
	public void initializePersons();

	// 3) on a besoin d'initialiser la listes des firestations
	public void initializeFirestations();

	// 4) on a besoin d'initialiser la listes des medicalRecord
	public void initializeMedicalrecords();

}
