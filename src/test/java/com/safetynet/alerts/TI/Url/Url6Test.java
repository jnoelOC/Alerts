package com.safetynet.alerts.TI.Url;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.dto.PersonUrl6DTO;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.service.UrlService;

@ExtendWith(MockitoExtension.class)
class Url6Test {

	// To be tested
	@InjectMocks
	private UrlService us = new UrlService();

	@Mock
	private PersonService personService;
	@Mock
	private MedicalRecordService medicalRecordService;

	@BeforeAll
	private static void setUp() {
	}

	@BeforeEach
	private void setUpPerTest() {
		List<PersonDTO> listOfAllPersons = new ArrayList<>();
		listOfAllPersons.add(new PersonDTO("John", "Boyd", "1509 Culver St", "Culver", "123456", "514-123-4567",
				"joBoyd@gmail.com"));
		listOfAllPersons.add(new PersonDTO("Tenley", "Boyd", "", "", "", "", ""));
		listOfAllPersons.add(new PersonDTO("Roger", "Boyd", "1509 Culver St", "Culver", "123456", "514-123-4567",
				"joBoyd@gmail.com"));
		listOfAllPersons.add(new PersonDTO("Ron", "Peters", "1509 Culver St", "Culver", "123456", "514-123-4567",
				"joBoyd@gmail.com"));
		listOfAllPersons.add(new PersonDTO("Eric", "Cadigan", "951 LoneTree Rd", "Culver", "123456", "514-123-4567",
				"joBoyd@gmail.com"));
		when(personService.findAllPersons()).thenReturn(listOfAllPersons);

		List<MedicalRecordDTO> listOfAllMedRec = new ArrayList<>();
		listOfAllMedRec.add(
				new MedicalRecordDTO("John", "Boyd", "03/03/1969", new String[] { "aspirine" }, new String[] { "" }));
		listOfAllMedRec.add(new MedicalRecordDTO("Roger", "Boyd", "12/06/2017", new String[] { "aspirine" },
				new String[] { "noix", "gluten" }));
		when(medicalRecordService.getAllMedicalRecords()).thenReturn(listOfAllMedRec);

	}

	// URL6
	/**
	 * This method checks getting infos from each person
	 * 
	 * @return List of lastname, address, age, mail and medical record from each
	 *         person
	 */
	@ParameterizedTest
	@MethodSource("PersonsSource")
	@DisplayName("Get infos from a firstname and lastname")
	void GetInfosFromFirstnameAndLastnameTest_ShouldReturnListOfPersonsWithInfos(String firstName, String lastName) {
		// arrange
		List<PersonUrl6DTO> lp;

		// act
		lp = us.getPersonWith(firstName, lastName);

		// assert
		assertNotNull(lp, "list of Persons not null");
	}

	// with its data
	private static Stream<Arguments> PersonsSource() {
		String firstname = "John";
		String lastname = "Boyd";
		String firstname2 = "Allison";
		String lastname2 = "Boyd";
		String firstname4 = "Ron";
		String lastname4 = "Peters";
		String firstname3 = "Roger";
		String lastname3 = "Boyd";

		return Stream.of(Arguments.of(firstname, lastname), Arguments.of(firstname2, lastname2),
				Arguments.of(firstname4, lastname4), Arguments.of(firstname3, lastname3));
	}

}