package com.safetynet.alerts.service;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dto.FirestationDTO;
import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.dto.PersonDTO;

@Service
public class JacksonService implements IJacksonService {

	public static final Logger logger = LogManager.getLogger(JacksonService.class);

	@Value("${jsonFile}")
	public String jsonFile;

	@Autowired
	private IPersonService personService;
	@Autowired
	private IFirestationService firestationService;
	@Autowired
	private IMedicalRecordService medicalrecordService;

	private JsonNode node;

	ObjectMapper mapper = new ObjectMapper();

	@PostConstruct
	public void getData() {

		try {
			this.node = mapper.readTree(new File(jsonFile));
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
