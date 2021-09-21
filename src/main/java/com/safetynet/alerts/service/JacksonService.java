package com.safetynet.alerts.service;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dto.FirestationDTO;
import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.dto.PersonDTO;

@Service
public class JacksonService implements IJacksonService {

	File myJsonFile = new File(
			"D:\\Documents\\OpenClassrooms\\P5-Chambe-Jean-Noel\\alerts-1\\src\\main\\resources\\data.json");
	ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private IPersonService personService;
	@Autowired
	private IFirestationService firestationService;
	@Autowired
	private IMedicalRecordService medicalrecordService;

	private JsonNode node;

	@PostConstruct
	public void getData() {

		try {
			this.node = mapper.readTree(myJsonFile);
		} catch (IOException e) {

			e.printStackTrace();
		}

		initializePersons();
		initializeFirestations();
		initializeMedicalrecords();

	}

	@Override
	public void initializePersons() {

		JsonNode nodePersons = this.node.path("persons");
		Iterator<JsonNode> iteratorPersons = nodePersons.elements();

		try {
			ObjectMapper mapper = new ObjectMapper();
			PersonDTO personDTO;
			int numPerson = 0;

			while (iteratorPersons.hasNext()) {
				personDTO = mapper.treeToValue(nodePersons.get(numPerson), PersonDTO.class);
				personService.addPerson(personDTO);
				numPerson++;
				iteratorPersons.next();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void initializeFirestations() {
		JsonNode nodeFirestations = this.node.path("firestations");
		Iterator<JsonNode> iteratorFirestations = nodeFirestations.elements();

		try {
			ObjectMapper mapper = new ObjectMapper();

			FirestationDTO firestationDTO;
			int numFirestation = 0;

			while (iteratorFirestations.hasNext()) {
				firestationDTO = mapper.treeToValue(nodeFirestations.get(numFirestation), FirestationDTO.class);
				firestationService.addFirestation(firestationDTO);
				numFirestation++;
				iteratorFirestations.next();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void initializeMedicalrecords() {
		JsonNode nodeMedicalrecords = this.node.path("medicalrecords");
		Iterator<JsonNode> iteratorMedicalrecords = nodeMedicalrecords.elements();

		try {
			ObjectMapper mapper = new ObjectMapper();

			MedicalRecordDTO medicalrecordDTO;
			int numMedicalrecord = 0;

			while (iteratorMedicalrecords.hasNext()) {
				medicalrecordDTO = mapper.treeToValue(nodeMedicalrecords.get(numMedicalrecord), MedicalRecordDTO.class);
				medicalrecordService.addMedicalRecord(medicalrecordDTO);
				numMedicalrecord++;
				iteratorMedicalrecords.next();
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
