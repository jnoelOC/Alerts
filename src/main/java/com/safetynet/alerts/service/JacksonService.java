package com.safetynet.alerts.service;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Person;

@Service
public class JacksonService implements IJacksonService {

	File myJsonFile = new File(
			"D:\\Documents\\OpenClassrooms\\P5-Chambe-Jean-Noel\\alerts-1\\src\\main\\resources\\data.json");
	ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private IPersonService personService;
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
			Person person;
			int numPerson = 0;

			while (iteratorPersons.hasNext()) {
				person = mapper.treeToValue(nodePersons.get(numPerson), Person.class);
				personService.addPerson(person);
				numPerson++;
				iteratorPersons.next();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void initializeFirestations() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initializeMedicalrecords() {
		// TODO Auto-generated method stub

	}

}
