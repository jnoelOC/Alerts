package com.safetynet.alerts.TI.Url;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.dto.FirestationDTO;
import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.service.FirestationService;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.service.UrlService;

@ExtendWith(MockitoExtension.class)
class Url3IT {

	// To be tested
	@InjectMocks
	private UrlService us = new UrlService();

	@Mock
	private FirestationService firestationService;
	@Mock
	private PersonService personService;

	@BeforeAll
	private static void setUp() {

	}

	@BeforeEach
	private void setUpPerTest() {
		List<FirestationDTO> listOfAllFireStations = new ArrayList<FirestationDTO>();
		listOfAllFireStations.add(new FirestationDTO("644 Gershwin Cir", "1"));
		listOfAllFireStations.add(new FirestationDTO("1509 Culver St", "3"));
		listOfAllFireStations.add(new FirestationDTO("29 15th St", "2"));
		listOfAllFireStations.add(new FirestationDTO("834 Binoc Ave", "3"));
		when(firestationService.getSeveralFirestations(Mockito.any())).thenReturn(listOfAllFireStations);

		List<PersonDTO> listOfAllPersons = new ArrayList<>();
		listOfAllPersons.add(new PersonDTO("John", "Boyd", "1509 Culver St", "Culver", "123456", "514-123-4561",
				"joBoyd@gmail.com"));
		listOfAllPersons
				.add(new PersonDTO("Tenley", "Boyd", "1509 Culver St", "Culver", "", "514-123-4565", "ten@gmail.com"));
		listOfAllPersons.add(new PersonDTO("Roger", "Boyd", "1509 Culver St", "Culver", "123456", "514-123-4562",
				"joBoyd@gmail.com"));
		listOfAllPersons.add(new PersonDTO("Ron", "Peters", "834 Binoc Ave", "Culver", "123456", "514-123-4563",
				"joBoyd@gmail.com"));
		listOfAllPersons.add(new PersonDTO("Eric", "Cadigan", "951 LoneTree Rd", "Culver", "123456", "514-123-4564",
				"joBoyd@gmail.com"));
		when(personService.findAllPersons()).thenReturn(listOfAllPersons);

	}

	// URL3
	/**
	 * This method checks getting phones of the same family from a station number
	 */
	@ParameterizedTest
	@MethodSource("StationsSource")
	@DisplayName("Get persons of the same family from a list of stations")
	void GetPhonesFromStationNumberTest_ShouldReturnListOfPhones(String stationNb) {
		// arrange
		Set<String> sPhone = new HashSet<>();

		// act
		sPhone = us.getPhonesFromFirestations(stationNb);

		// assert
		assertNotNull(sPhone, "list of phones not null");
	}

	// with its data
	private static Stream<Arguments> StationsSource() {
		String stationNb = "1";
		String station = "3";

		return Stream.of(Arguments.of(stationNb), Arguments.of(station));
	}
}