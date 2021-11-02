package com.safetynet.alerts.TU;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.dto.FirestationDTO;
import com.safetynet.alerts.dto.PersonUrl4DTO;
import com.safetynet.alerts.service.IFirestationService;
import com.safetynet.alerts.service.UrlService;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class Url5Test {

	// To be tested
	UrlService us = new UrlService();

	@Mock
	IFirestationService firestationService;

	@BeforeAll
	private void setUp() {
		List<FirestationDTO> listOfAllFireStations = new ArrayList<FirestationDTO>();
		listOfAllFireStations.add(new FirestationDTO("1", "644 Gershwin Cir"));
		listOfAllFireStations.add(new FirestationDTO("3", "1509 Culver St"));
		listOfAllFireStations.add(new FirestationDTO("2", "29 15th St"));
		listOfAllFireStations.add(new FirestationDTO("3", "834 Binoc Ave"));

		when(firestationService.getAllFirestations()).thenReturn(listOfAllFireStations);

	}

	@BeforeEach
	private void setUpPerTest() {

	}

	// URL5
	/**
	 * This method checks getting persons of the same family from a list of stations
	 */
	@ParameterizedTest
	@MethodSource("StationsSource")
	@DisplayName("Get persons of the same family from a list of stations")
	void GetFamilyFromListOfStationsTest_ShouldReturnListOfPersonsWithInfos(List<String> listOfFirestationNb) {
		// arrange
		List<PersonUrl4DTO> ls = new ArrayList<>();

		// act
		ls = us.getFamilyWith(listOfFirestationNb);

		// assert
		assertNotNull(ls, "list of Persons not null");
	}

	// with its data
	private static Stream<Arguments> StationsSource() {
		List<String> listOfFirestationNb = new ArrayList<>();
		List<String> stations = new ArrayList<>();
		List<String> stationsNb = new ArrayList<>();
		List<String> firestationsNb = null;

		for (Integer i = 1; i <= 5; i++) {
			listOfFirestationNb.add(i.toString());
		}
		stations.add("3");
		stationsNb.add("");

		return Stream.of(Arguments.of(listOfFirestationNb), Arguments.of(stations), Arguments.of(stationsNb),
				Arguments.of(firestationsNb));
	}

}
