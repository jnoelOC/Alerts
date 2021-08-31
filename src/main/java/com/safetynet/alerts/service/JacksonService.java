package com.safetynet.alerts.service;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Firestations;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Persons;

public class JacksonService {

	File myJsonFile = new File(
			"D:\\Documents\\OpenClassrooms\\P5-Chambe-Jean-Noel\\alerts-1\\src\\main\\resources\\data.json");
	ObjectMapper mapper = new ObjectMapper();

	public Persons ReadPersonsFromJson() {
		Persons obj = null;
		try {
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			obj = mapper.readValue(myJsonFile, Persons.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj;
	}

	public void WritePersonsToJson(Persons allPersons) {
		try {

			mapper.writeValue(myJsonFile, allPersons);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Firestations ReadFirestationsFromJson() {
		Firestations obj = null;
		try {
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			obj = mapper.readValue(myJsonFile, Firestations.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj;
	}

	public MedicalRecords ReadMedicalRecordsFromJson() {
		MedicalRecords obj = null;
		try {
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			obj = mapper.readValue(myJsonFile, MedicalRecords.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj;
	}

}
