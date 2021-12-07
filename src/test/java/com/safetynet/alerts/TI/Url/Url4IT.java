package com.safetynet.alerts.TI.Url;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.safetynet.alerts.dto.FirestationDTO;
import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.dto.PersonsUrl4DTO;
import com.safetynet.alerts.service.FirestationService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.service.UrlService;

@ExtendWith(MockitoExtension.class)
class Url4IT {

	// To be tested
	@InjectMocks
	private UrlService us = new UrlService();

	@Mock
	private FirestationService firestationService;
	@Mock
	private PersonService personService;
	@Mock
	private MedicalRecordService medicalRecordService;

	@BeforeAll
	private static void setUp() {

	}

	@BeforeEach
	private void setUpPerTest() {

		List<FirestationDTO> listOfAllFireStations = new ArrayList<FirestationDTO>();
		listOfAllFireStations.add(new FirestationDTO("1509 Culver St", "3"));
		listOfAllFireStations.add(new FirestationDTO("644 Gershwin Cir", "1"));
		listOfAllFireStations.add(new FirestationDTO("951 LoneTree Rd", "2"));
		listOfAllFireStations.add(new FirestationDTO("834 Binoc Ave", "3"));
		when(firestationService.getAllFirestations()).thenReturn(listOfAllFireStations);

		List<PersonDTO> listOfAllPersons = new ArrayList<>();
		listOfAllPersons.add(new PersonDTO("John", "Boyd", "1509 Culver St", "Culver", "123456", "514-123-4567",
				"joBoyd@gmail.com"));
		listOfAllPersons.add(new PersonDTO("Tenley", "Boyd", "", "", "", "", ""));
		listOfAllPersons.add(new PersonDTO("Roger", "Boyd", "1509 Culver St", "Culver", "123456", "514-123-4567",
				"joBoyd@gmail.com"));
		listOfAllPersons.add(new PersonDTO("Ron", "Peters", "1509 Culver St", "Culver", "123456", "514-123-4567",
				"joBoyd@gmail.com"));
		listOfAllPersons.add(new PersonDTO("Eric", "Cadigan", "951 LoneTree Rd", "Culver", "123456", "514-123-4567",
				"ericc@gmail.com"));
		when(personService.findAllPersons()).thenReturn(listOfAllPersons);

	}

	// URL4
	/**
	 * This method checks getting persons at address from a station address
	 * 
	 * @return List of persons living in address associated with this station
	 *         address
	 */
	@ParameterizedTest
	@MethodSource("StationsAddressesSource")
	@DisplayName("Get persons at address from a station address")
	void GetPersonsFromStationsAddressesTest_ShouldReturnListOfPersonsWithInfos(String address) {
		// arrange
		PersonsUrl4DTO persons = new PersonsUrl4DTO();

		// act
		persons = us.getPersonsWithFirestationsFrom(address);

		// assert
		assertNotNull(persons, "list of Persons not null");
	}

	// with its data
	private static Stream<Arguments> StationsAddressesSource() {
		String address1 = "951 LoneTree Rd";
		String address2 = "1509 Culver St";

		return Stream.of(Arguments.of(address1), Arguments.of(address2));
	}

	/**
	 * This method checks getting persons at address from a null station address
	 * 
	 * @return null
	 */
	@ParameterizedTest
	@MethodSource("NullStationsAddressesSource")
	@DisplayName("Get persons at address from a null station address")
	void GetPersonsFromNullStationsAddressesTest_ShouldReturnNull(String address) {
		// arrange
		PersonsUrl4DTO persons = new PersonsUrl4DTO();

		// act
		persons = us.getPersonsWithFirestationsFrom(address);

		// assert
		assertThat(persons.getListOfPersons()).isNull();
	}

	// with its data
	private static Stream<Arguments> NullStationsAddressesSource() {
		String address3 = "834 Binoc Ave";
		String address4 = "123 route de la soie";
		String address5 = null;

		return Stream.of(Arguments.of(address3), Arguments.of(address4), Arguments.of(address5));
	}

}
