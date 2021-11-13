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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.dto.FirestationDTO;
import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.dto.PersonsInfoDTO;
import com.safetynet.alerts.service.FirestationService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.service.UrlService;

@ExtendWith(MockitoExtension.class)
class Url1Test {

	// To be tested
	@InjectMocks
	private UrlService us = new UrlService();

	@Mock
	private PersonService personService;
	@Mock
	private FirestationService firestationService;
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
		listOfAllPersons.add(new PersonDTO("Tenley", "Boyd", "1509 Culver St", "Culver", "123456", "514-123-4567",
				"joBoyd@gmail.com"));
		listOfAllPersons.add(new PersonDTO("Roger", "Boyd", "1509 Culver St", "Culver", "123456", "514-123-4567",
				"joBoyd@gmail.com"));
		listOfAllPersons.add(new PersonDTO("Ron", "Peters", "1509 Culver St", "Culver", "123456", "514-123-4567",
				"joBoyd@gmail.com"));
		listOfAllPersons.add(new PersonDTO("Eric", "Cadigan", "951 LoneTree Rd", "Culver", "123456", "514-123-4567",
				"joBoyd@gmail.com"));
		when(personService.findAllPersons()).thenReturn(listOfAllPersons);

		List<FirestationDTO> listOfAllFireStations = new ArrayList<FirestationDTO>();
		listOfAllFireStations.add(new FirestationDTO("644 Gershwin Cir", "1"));
		listOfAllFireStations.add(new FirestationDTO("1509 Culver St", "3"));
		listOfAllFireStations.add(new FirestationDTO("29 15th St", "2"));
		listOfAllFireStations.add(new FirestationDTO("834 Binoc Ave", "3"));
		when(firestationService.getSeveralFirestations(Mockito.any())).thenReturn(listOfAllFireStations);

		List<MedicalRecordDTO> listOfAllMedRec = new ArrayList<>();
		listOfAllMedRec.add(
				new MedicalRecordDTO("John", "Boyd", "03/03/1969", new String[] { "aspirine" }, new String[] { "" }));
		listOfAllMedRec.add(new MedicalRecordDTO("eric", "cadigan", "12/06/1980", new String[] { "aspirine" },
				new String[] { "gluten" }));
		listOfAllMedRec.add(new MedicalRecordDTO("ron", "peters", "12/06/1998", new String[] { "aspirine" },
				new String[] { "gluten" }));
		listOfAllMedRec.add(new MedicalRecordDTO("Tenley", "Boyd", "12/06/2009", new String[] { "aspirine" },
				new String[] { "noix", "gluten" }));
		listOfAllMedRec.add(new MedicalRecordDTO("Roger", "Boyd", "12/06/2017", new String[] { "aspirine" },
				new String[] { "noix", "gluten" }));
		when(medicalRecordService.getAllMedicalRecords()).thenReturn(listOfAllMedRec);

	}

	// URL1
	/**
	 * This method checks getting persons (with nb of children and adults) covered
	 * by corresponding station
	 * 
	 * @return List of persons (with nb of children and adults)
	 */
	@ParameterizedTest
	@MethodSource("StationNbSource")
	@DisplayName("Get persons covered by station")
	void GetPersonsFromStationNumberTest_ShouldReturnListOfPersonsWithInfos(String stationNumber) {
		// arrange
		PersonsInfoDTO piDto = new PersonsInfoDTO();
		// act
		piDto = us.getPersonsWithBirthdatesFromFirestations(stationNumber);

		// assert
		assertNotNull(piDto, "object of children and adults not null");
	}

	// with its data
	private static Stream<Arguments> StationNbSource() {
		String station1 = "1";
		String station2 = "2";
		String station3 = "3";

		return Stream.of(Arguments.of(station1), Arguments.of(station2), Arguments.of(station3));
	}

}
