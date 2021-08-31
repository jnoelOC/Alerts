package com.safetynet.alerts.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.Persons;
import com.safetynet.alerts.service.JacksonService;

@Repository
public class PersonRepository {

	JacksonService player = new JacksonService();
	Persons allPersons = player.ReadPersonsFromJson();

	public List<Person> getAllPersons() {

		return allPersons.getPersons();
	}

	public Person getInfoAboutOnePerson(String firstname, String lastname) {
		Person p = new Person();

		for (Integer index = 0; index < allPersons.getPersons().size(); index++) {
			if (firstname.equalsIgnoreCase(allPersons.getPersons().get(index).getFirstName())
					&& lastname.equalsIgnoreCase(allPersons.getPersons().get(index).getLastName())) {
				p.setFirstName(allPersons.getPersons().get(index).getFirstName());
				p.setLastName(allPersons.getPersons().get(index).getLastName());
				p.setAddress(allPersons.getPersons().get(index).getAddress());
				p.setCity(allPersons.getPersons().get(index).getCity());
				p.setZip(allPersons.getPersons().get(index).getZip());
				p.setPhone(allPersons.getPersons().get(index).getPhone());
				p.setEmail(allPersons.getPersons().get(index).getEmail());
			}
		}
		return p;
	}

	public void updateInfoAboutOnePerson(String firstname, String lastname) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		for (Integer index = 0; index < allPersons.getPersons().size(); index++) {
			if (firstname.equalsIgnoreCase(allPersons.getPersons().get(index).getFirstName())
					&& lastname.equalsIgnoreCase(allPersons.getPersons().get(index).getLastName())) {
				// ask for keyboard to address, city, zip, phone and email
				System.out.println("Enter address : ");
				String addr = reader.readLine();
				System.out.println("Enter zip : ");
				String zip = reader.readLine();
				System.out.println("Enter city : ");
				String city = reader.readLine();
				System.out.println("Enter phone : ");
				String phone = reader.readLine();
				System.out.println("Enter mail : ");
				String mail = reader.readLine();
				// fill data
				allPersons.getPersons().get(index).setAddress(addr);
				allPersons.getPersons().get(index).setCity(city);
				allPersons.getPersons().get(index).setZip(zip);
				allPersons.getPersons().get(index).setPhone(phone);
				allPersons.getPersons().get(index).setEmail(mail);
				// write data to Json file
				player.WritePersonsToJson(allPersons);

			}
		}

	}

}
