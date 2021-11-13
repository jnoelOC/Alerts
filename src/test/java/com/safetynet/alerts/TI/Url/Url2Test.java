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

import com.safetynet.alerts.dto.ChildrenInfoDTO;
import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.service.UrlService;

@ExtendWith(MockitoExtension.class)
class Url2Test {

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
		listOfAllPersons.add(new PersonDTO("Tenley", "Boyd", "1509 Culver St", "Culver", "123456", "514-123-4567",
				"joBoyd@gmail.com"));
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

	// URL2
	/**
	 * This method checks getting children living at this address with adults
	 * 
	 * @return List of children
	 */
	@ParameterizedTest
	@MethodSource("AddressesSource")
	@DisplayName("Get children living at this address with adults")
	void GetChildrenFromAddressTest_ShouldReturnListOfChildrenWithInfos(String address) {
		// arrange
		ChildrenInfoDTO ciDto = new ChildrenInfoDTO();

		// act
		ciDto = us.getChildrenFrom(address);

		// assert
		assertNotNull(ciDto, "object of children not null");
	}

	// with its data
	private static Stream<Arguments> AddressesSource() {
		String address1 = "1509 Culver St";
		String address2 = "951 LoneTree Rd";
//		String address3 = "834 Binoc Ave";
//		String address4 = "123 route de la soie";
//		String address5 = null;

		return Stream.of(Arguments.of(address1), Arguments.of(address2)
//				, Arguments.of(address3), Arguments.of(address4),Arguments.of(address5)
		);
	}

}
