package com.safetynet.alerts.TU.Srvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.dto.ChildrenInfoDTO;
import com.safetynet.alerts.dto.FirestationDTO;
import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.dto.PersonUrl4DTO;
import com.safetynet.alerts.dto.PersonUrl6DTO;
import com.safetynet.alerts.dto.PersonsInfoDTO;
import com.safetynet.alerts.dto.PersonsUrl4DTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.FirestationService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.service.UrlService;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {
	// To be tested
	@InjectMocks
	private UrlService urlService;

	@Mock
	private MedicalRecordService medicalRecordService;
	@Mock
	private PersonService personService;
	@Mock
	private FirestationService firestationService;

	@BeforeAll
	private static void setUp() {
	}

	@BeforeEach
	private void setUpPerTest() {

	}

	/**
	 * This method checks finding all MedicalRecordDTO
	 */
	@Test
	@DisplayName("Find all MedRecDTO")
	void FindAllMedRecTest_ShouldReturnListOfMedicalRecordDTO() {
		// Arrange
		MedicalRecordDTO medicalRecordDTO1 = new MedicalRecordDTO("victor", "hugo", "12/12/1805",
				new String[] { "aspirine", "doliprane" }, new String[] { "gluten" });
		MedicalRecordDTO medicalRecordDTO2 = new MedicalRecordDTO("gustave", "flaubert", "02/02/1825",
				new String[] { "efferalgan" }, new String[] { "maïs" });
		List<MedicalRecordDTO> listMR = new ArrayList<MedicalRecordDTO>();
		listMR.add(medicalRecordDTO1);
		listMR.add(medicalRecordDTO2);
		when(medicalRecordService.getAllMedicalRecords()).thenReturn(listMR);

		// Act
		List<MedicalRecordDTO> listMRDTO = urlService.findAllMedicalRecord();
		// Assert
		assertNotNull(listMRDTO);
	}

	/**
	 * This method checks retrieving all persons associated with an address
	 */
	@ParameterizedTest
	@MethodSource("PersonDTOSource")
	@DisplayName("Retrieve all PersonDTO")
	void RetrieveAllPersonsWithAddressTest_ShouldReturnListOfMedicalRecordDTO(String address) {
		// Arrange
		PersonDTO personDTO1 = new PersonDTO("victor", "hugo", "1509 Culver st", "Culver", "123456", "0123456789",
				"vic@gmail.com");
		PersonDTO personDTO2 = new PersonDTO("gustave", "flaubert", "1509 Culver st", "Culver", "123456", "0198765432",
				"gus@gmail.com");
		List<PersonDTO> listPersons = new ArrayList<PersonDTO>();
		listPersons.add(personDTO1);
		listPersons.add(personDTO2);
		when(personService.findAllPersons()).thenReturn(listPersons);
		// Act
		List<PersonDTO> listPersonsDTO = urlService.retrieveAllPersonsFrom(address);
		// Assert
		assertNotNull(listPersonsDTO);
	}

	// with its data
	private static Stream<Arguments> PersonDTOSource() {
		String addr1 = "5 rue du terrier";
		String addr2 = "1509 Culver st";
		String addr3 = "5 rue de la taniere";
		return Stream.of(Arguments.of(addr1), Arguments.of(addr2), Arguments.of(addr3));
	}

	/**
	 * This method checks retrieving a list of persons by comparing address of
	 * persons with address of stations
	 */
	@ParameterizedTest
	@MethodSource("AddressesSource")
	@DisplayName("Retrieve a list of persons with same station address")
	void RetrieveAListOfPersonsWithSameAddressTest_ShouldReturnListOfPersons(List<Firestation> listOfFirestations,
			List<Person> listOfPersons) {
		// Arrange
		// Act
		List<Person> listPersons = urlService.retrievePersonsWithSameStationAddress(listOfFirestations, listOfPersons);
		// Assert
		assertNotNull(listPersons);
	}

	// with its data
	private static Stream<Arguments> AddressesSource() {
		Person person1 = new Person("victor", "hugo", "1509 Culver st", "Culver", "123456", "0123456789",
				"vic@gmail.com");
		Person person2 = new Person("gustave", "flaubert", "1510 Culver st", "Culver", "123456", "0198765432",
				"gus@gmail.com");
		List<Person> listPersons1 = new ArrayList<Person>();
		listPersons1.add(person1);
		listPersons1.add(person2);

		Firestation fs1 = new Firestation("1509 Culver st", "3");
		Firestation fs2 = new Firestation("1511 Culver st", "1");
		List<Firestation> listFs1 = new ArrayList<Firestation>();
		listFs1.add(fs1);
		listFs1.add(fs2);

		Person person3 = new Person("victor", "hugo", "1509 Culver st", "Culver", "123456", "0123456789",
				"vic@gmail.com");
		Person person4 = new Person("gustave", "flaubert", "1510 Culver st", "Culver", "123456", "0198765432",
				"gus@gmail.com");
		List<Person> listPersons2 = new ArrayList<Person>();
		listPersons1.add(person3);
		listPersons1.add(person4);

		Firestation fs3 = new Firestation("1509 Culver st", "3");
		Firestation fs4 = new Firestation("1510 Culver st", "1");
		List<Firestation> listFs2 = new ArrayList<Firestation>();
		listFs1.add(fs3);
		listFs1.add(fs4);
		return Stream.of(Arguments.of(listFs1, listPersons1), Arguments.of(listFs2, listPersons2));
	}

	/**
	 * This method checks calculating age of a person with its firstname and
	 * lastname
	 */
	@ParameterizedTest
	@MethodSource("NamesSource")
	@DisplayName("Calculate age of a person with its firstname and lastname")
	void CalculateAgeOfPersonsWithNamesTest_ShouldReturnAIntegerAge(String firstName, String lastName) {
		// Arrange
		MedicalRecordDTO medicalRecordDTO1 = new MedicalRecordDTO("victor", "hugo", "12/12/2006",
				new String[] { "aspirine", "doliprane" }, new String[] { "gluten" });
		MedicalRecordDTO medicalRecordDTO2 = new MedicalRecordDTO("gustave", "flaubert", "02/02/1825",
				new String[] { "efferalgan" }, new String[] { "maïs" });
		List<MedicalRecordDTO> listMR = new ArrayList<MedicalRecordDTO>();
		listMR.add(medicalRecordDTO1);
		listMR.add(medicalRecordDTO2);
		when(medicalRecordService.getAllMedicalRecords()).thenReturn(listMR);
		// Act
		Integer age = urlService.computeAge(firstName, lastName);
		// Assert
		assertNotNull(age);
	}

	// with its data
	private static Stream<Arguments> NamesSource() {
		String firstNam1 = "victor";
		String lastNam1 = "hugo";
		String firstNam2 = "gustave";
		String lastNam2 = "flaub";

		return Stream.of(Arguments.of(firstNam1, lastNam1), Arguments.of(firstNam2, lastNam2));
	}

	/**
	 * This method checks filling medical record DTO with data
	 */
	@ParameterizedTest
	@MethodSource("DataSource")
	@DisplayName("Fill medical record DTO with data")
	void FillMedRecDTOWithDataTest_ShouldReturnAMedicalRecordDTO(MedicalRecordDTO medRecDTO, String firstName,
			String lastName) {
		// Arrange
		MedicalRecordDTO medicalRecordDTO1 = new MedicalRecordDTO("victor", "hugo", "12/12/2006",
				new String[] { "aspirine", "doliprane" }, new String[] { "gluten" });
		MedicalRecordDTO medicalRecordDTO2 = new MedicalRecordDTO("gustave", "flaubert", "02/02/1825",
				new String[] { "efferalgan" }, new String[] { "maïs" });
		List<MedicalRecordDTO> listMR = new ArrayList<MedicalRecordDTO>();
		listMR.add(medicalRecordDTO1);
		listMR.add(medicalRecordDTO2);
		when(medicalRecordService.getAllMedicalRecords()).thenReturn(listMR);
		// Act
		MedicalRecordDTO mRDTO = urlService.fillMedicalRecordDTO(medRecDTO, firstName, lastName);
		// Assert
		assertNotNull(mRDTO);
	}

	// with its data
	private static Stream<Arguments> DataSource() {
		String firstNam1 = "victor";
		String lastNam1 = "hugo";
		String firstNam2 = "gustave";
		String lastNam2 = "flaub";
		MedicalRecordDTO medicalRecordDTO1 = new MedicalRecordDTO("victor", "hugo", "12/12/2006",
				new String[] { "aspirine", "doliprane" }, new String[] { "gluten" });
		MedicalRecordDTO medicalRecordDTO2 = new MedicalRecordDTO("gustave", "flaubert", "02/02/1825",
				new String[] { "efferalgan" }, new String[] { "maïs" });

		return Stream.of(Arguments.of(medicalRecordDTO1, firstNam1, lastNam1),
				Arguments.of(medicalRecordDTO2, firstNam2, lastNam2));
	}

	/**
	 * This method checks Transforming from a person to personInfoDTO
	 */
	@ParameterizedTest
	@MethodSource("PersonSource")
	@DisplayName("Transform from a person to personInfoDTO")
	void TransformPersonsInPersonDTOTest_ShouldReturnListOfPersonDTO(List<Person> listOfPersonsCoveredByFirestation) {
		// Arrange
		// Act
		List<PersonInfoDTO> listPersonsInfoDTO = urlService
				.transformFromPersonToPersonInfoDTO(listOfPersonsCoveredByFirestation);
		// Assert
		assertNotNull(listPersonsInfoDTO);
	}

	// with its data
	private static Stream<Arguments> PersonSource() {
		Person person1 = new Person("victor", "hugo", "1509 Culver st", "Culver", "123456", "0123456789",
				"vic@gmail.com");
		Person person2 = new Person("gustave", "flaubert", "1509 Culver st", "Culver", "123456", "0198765432",
				"gus@gmail.com");
		List<Person> listPersons = new ArrayList<Person>();
		listPersons.add(person1);
		listPersons.add(person2);
		return Stream.of(Arguments.of(listPersons));
	}

	/**
	 * This method checks retrieving a station from a stationNumber
	 */
	@ParameterizedTest
	@MethodSource("StationSource")
	@DisplayName("Retrieve a station from a stationNumber")
	void RetrieveAStationAddressWithStationNumberTest_ShouldReturnListOfFirestations(String stationNumber) {
		// Arrange
		FirestationDTO fs1 = new FirestationDTO("1509 Culver st", "3");
		FirestationDTO fs2 = new FirestationDTO("1511 Culver st", "1");
		List<FirestationDTO> listFs = new ArrayList<FirestationDTO>();
		listFs.add(fs1);
		listFs.add(fs2);
		when(firestationService.getSeveralFirestations(stationNumber)).thenReturn(listFs);
		// Act
		List<Firestation> listFirestation = urlService.retrieveStationsAssociatedWith(stationNumber);
		// Assert
		assertNotNull(listFirestation);
	}

	// with its data
	private static Stream<Arguments> StationSource() {
		String station1 = "1";
		String station2 = "2";
		String station3 = "3";
		return Stream.of(Arguments.of(station1), Arguments.of(station2), Arguments.of(station3));
	}

	/**
	 * This method checks Retrieving a list of persons from a TreeMap listOfPersons
	 */
	@ParameterizedTest
	@MethodSource("PersonsSource")
	@DisplayName("Retrieve a list of persons from a TreeMap listOfPersons")
	void RetrieveListOfPersonsFromTreemapTest_ShouldReturnListOfPersons(TreeMap<String, String> listOfPersons) {
		// Arrange
		PersonDTO personDto = new PersonDTO("victor", "hugo", "1509 Culver st", "Culver", "123456", "0123456789",
				"vic@gmail.com");
		when(personService.getOnePerson(Mockito.anyString(), Mockito.anyString())).thenReturn(personDto);
		// Act
		List<Person> listPersons = urlService.retrieveAListOfPersonsFrom(listOfPersons);
		// Assert
		assertNotNull(listPersons);
	}

	// with its data
	private static Stream<Arguments> PersonsSource() {
		String firstNam1 = "victor";
		String lastNam1 = "hugo";
		String firstNam2 = "gustave";
		String lastNam2 = "flaub";
		TreeMap<String, String> listOfPersons = new TreeMap<>();
		listOfPersons.put(firstNam1, lastNam1);
		listOfPersons.put(firstNam2, lastNam2);

		return Stream.of(Arguments.of(listOfPersons));
	}

	/**
	 * This method checks retrieving a medical record from firstname and lastname
	 */
	@ParameterizedTest
	@MethodSource("MedRecSource")
	@DisplayName("Retrieve a medical record from firstname and lastname")
	void RetrieveAMedicalRecordTest_ShouldReturnAMedicalRecord(String firstName, String lastName) {
		// Arrange
		MedicalRecordDTO medicalRecordDTO1 = new MedicalRecordDTO("victor", "hugo", "12/12/2006",
				new String[] { "aspirine", "doliprane" }, new String[] { "gluten" });
		MedicalRecordDTO medicalRecordDTO2 = new MedicalRecordDTO("gustave", "flaubert", "02/02/1825",
				new String[] { "efferalgan" }, new String[] { "maïs" });
		List<MedicalRecordDTO> listMrDTO = new ArrayList<MedicalRecordDTO>();
		listMrDTO.add(medicalRecordDTO1);
		listMrDTO.add(medicalRecordDTO2);
		when(urlService.findAllMedicalRecord()).thenReturn(listMrDTO);
		// Act
		MedicalRecord medRec = urlService.retrieveMedRec(firstName, lastName);
		// Assert
		assertNotNull(medRec);
	}

	// with its data
	private static Stream<Arguments> MedRecSource() {
		String firstNam1 = "victor";
		String lastNam1 = "hugo";
		String firstNam3 = "gustave";
		String lastNam3 = "flaubert";
		return Stream.of(Arguments.of(firstNam1, lastNam1), Arguments.of(firstNam3, lastNam3));
	}

	/**
	 * This method checks retrieving a medical record from firstname and lastname
	 */
	@ParameterizedTest
	@MethodSource("MedRec2Source")
	@DisplayName("Retrieve null from firstname and lastname")
	void RetrieveAMedicalRecordTest_ShouldReturnNull(String firstName, String lastName) {
		// Arrange
		MedicalRecordDTO medicalRecordDTO1 = new MedicalRecordDTO("victor", "hugo", "12/12/2006",
				new String[] { "aspirine", "doliprane" }, new String[] { "gluten" });
		MedicalRecordDTO medicalRecordDTO2 = new MedicalRecordDTO("gustave", "flaubert", "02/02/1825",
				new String[] { "efferalgan" }, new String[] { "maïs" });
		List<MedicalRecordDTO> listMrDTO = new ArrayList<MedicalRecordDTO>();
		listMrDTO.add(medicalRecordDTO1);
		listMrDTO.add(medicalRecordDTO2);
		when(urlService.findAllMedicalRecord()).thenReturn(listMrDTO);
		// Act
		MedicalRecord medRec = urlService.retrieveMedRec(firstName, lastName);
		// Assert
		assertNull(medRec);
	}

	// with its data
	private static Stream<Arguments> MedRec2Source() {
		String firstNam1 = "";
		String lastNam1 = null;
		String firstNam2 = "gustave";
		String lastNam2 = "flaub";
		return Stream.of(Arguments.of(firstNam1, lastNam1), Arguments.of(firstNam2, lastNam2));
	}

	/**
	 * This method checks finding list of persons with same family's name
	 */
	@ParameterizedTest
	@MethodSource("PersonsNameSource")
	@DisplayName("Find list of persons with same family's name")
	void FindAllPersonsWithSameLastnameTest_ShouldReturnListOfPersonsDTO(String lastName) {
		// Arrange
		PersonDTO person1 = new PersonDTO("victor", "hugo", "1509 Culver st", "Culver", "123456", "0123456789",
				"vic@gmail.com");
		PersonDTO person2 = new PersonDTO("gustave", "flaubert", "1509 Culver st", "Culver", "123456", "0198765432",
				"gus@gmail.com");
		List<PersonDTO> listPersons = new ArrayList<PersonDTO>();
		listPersons.add(person1);
		listPersons.add(person2);
		when(personService.findAllPersons()).thenReturn(listPersons);

		// Act
		List<PersonDTO> listPersonsDTO = urlService.getPersonWithSameFamily(lastName);
		// Assert
		assertNotNull(listPersonsDTO);
	}

	// with its data
	private static Stream<Arguments> PersonsNameSource() {

		String lastNam1 = "hugo";
		String lastNam2 = "flaubert";
		String lastNam3 = "chateaubriand";
		return Stream.of(Arguments.of(lastNam1), Arguments.of(lastNam2), Arguments.of(lastNam3));
	}

	/**
	 * URL7 This method checks getting emails of all Persons from a city
	 */
	@ParameterizedTest
	@MethodSource("CitySource")
	@DisplayName("Get emails of persons from the same city")
	void GetEmailsOfPersonsFromCityTest_ShouldReturnListOfEmails(String city) {
		// arrange
		List<String> listOfAllMails = new ArrayList<>();
		listOfAllMails.add("jo@gmail.com");
		listOfAllMails.add("ro@gmail.com");
		listOfAllMails.add("tenley@gmail.com");
		listOfAllMails.add("allison@gmail.com");
		when(personService.getAllEmailsFrom(Mockito.any())).thenReturn(listOfAllMails);

		List<String> ls = new ArrayList<>();

		// act
		ls = urlService.getAllEmailsFrom(city);
		// assert
		assertNotNull(ls, "list of Emails not null");
	}

	// with its data
	private static Stream<Arguments> CitySource() {
		String city1 = "Culver";
		return Stream.of(Arguments.of(city1));
	}

	/**
	 * URL6 This method checks getting infos from each person
	 * 
	 * @return List of lastname, address, age, mail and medical record from each
	 *         person
	 */
	@ParameterizedTest
	@MethodSource("Person6Source")
	@DisplayName("Get infos from a firstname and lastname")
	void GetInfosFromFirstnameAndLastnameTest_ShouldReturnListOfPersonsWithInfos(String firstName, String lastName) {
		// arrange
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

		List<PersonUrl6DTO> lp;

		// act
		lp = urlService.getPersonWith(firstName, lastName);

		// assert
		assertNotNull(lp, "list of Persons not null");
	}

	// with its data
	private static Stream<Arguments> Person6Source() {
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

	/**
	 * URL5 This method checks getting persons of the same family from a list of
	 * stations
	 */
	@ParameterizedTest
	@MethodSource("StationsSource")
	@DisplayName("Get persons of the same family from a list of stations")
	void GetFamilyFromListOfStationsTest_ShouldReturnListOfPersonsWithInfos(List<String> listOfFirestationNb) {
		// arrange
		List<FirestationDTO> listOfAllFireStations = new ArrayList<FirestationDTO>();
		listOfAllFireStations.add(new FirestationDTO("644 Gershwin Cir", "1"));
		listOfAllFireStations.add(new FirestationDTO("1509 Culver St", "3"));
		listOfAllFireStations.add(new FirestationDTO("29 15th St", "2"));
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
				"joBoyd@gmail.com"));
		when(personService.findAllPersons()).thenReturn(listOfAllPersons);

		List<MedicalRecordDTO> listOfAllMedRec = new ArrayList<>();
		listOfAllMedRec.add(
				new MedicalRecordDTO("John", "Boyd", "03/03/1969", new String[] { "aspirine" }, new String[] { "" }));
		listOfAllMedRec.add(new MedicalRecordDTO("Roger", "Boyd", "12/06/2017", new String[] { "aspirine" },
				new String[] { "noix", "gluten" }));
		when(medicalRecordService.getAllMedicalRecords()).thenReturn(listOfAllMedRec);
		List<PersonUrl4DTO> ls = new ArrayList<>();

		// act
		ls = urlService.getFamilyWith(listOfFirestationNb);

		// assert
		assertNotNull(ls, "list of Persons not null");
	}

	// with its data
	private static Stream<Arguments> StationsSource() {
		List<String> listOfFirestationNb = new ArrayList<>();
		List<String> stations = new ArrayList<>();

		for (Integer i = 1; i <= 5; i++) {
			listOfFirestationNb.add(i.toString());
		}
		stations.add("3");

		return Stream.of(Arguments.of(listOfFirestationNb), Arguments.of(stations));
	}

	/**
	 * URL4 This method checks getting persons at address from a station address
	 * 
	 * @return List of persons living in address associated with this station
	 *         address
	 */
	@ParameterizedTest
	@MethodSource("StationsAddressesSource")
	@DisplayName("Get persons at address from a station address")
	void GetPersonsFromStationsAddressesTest_ShouldReturnListOfPersonsWithInfos(String address) {
		// arrange
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
		PersonsUrl4DTO persons = new PersonsUrl4DTO();

		// act
		persons = urlService.getPersonsWithFirestationsFrom(address);

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
		PersonsUrl4DTO persons = new PersonsUrl4DTO();

		// act
		persons = urlService.getPersonsWithFirestationsFrom(address);

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

	/**
	 * URL3 This method checks getting phones of the same family from a station
	 * number
	 */
	@ParameterizedTest
	@MethodSource("Stations3Source")
	@DisplayName("Get persons of the same family from a list of stations")
	void GetPhonesFromStationNumberTest_ShouldReturnListOfPhones(String stationNb) {
		// arrange
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
		Set<String> sPhone = new HashSet<>();

		// act
		sPhone = urlService.getPhonesFromFirestations(stationNb);

		// assert
		assertNotNull(sPhone, "list of phones not null");
	}

	// with its data
	private static Stream<Arguments> Stations3Source() {
		String stationNb = "1";
		String station = "3";

		return Stream.of(Arguments.of(stationNb), Arguments.of(station));
	}

	/**
	 * URL2 This method checks getting children living at this address with adults
	 * 
	 * @return List of children
	 */
	@ParameterizedTest
	@MethodSource("Addresses2Source")
	@DisplayName("Get children living at this address with adults")
	void GetChildrenFromAddressTest_ShouldReturnListOfChildrenWithInfos(String address) {
		// arrange
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
		ChildrenInfoDTO ciDto = new ChildrenInfoDTO();

		// act
		ciDto = urlService.getChildrenFrom(address);

		// assert
		assertNotNull(ciDto, "object of children not null");
	}

	// with its data
	private static Stream<Arguments> Addresses2Source() {
		String address1 = "1509 Culver St";
		String address2 = "951 LoneTree Rd";

		return Stream.of(Arguments.of(address1), Arguments.of(address2));
	}

	/**
	 * URL1 This method checks getting persons (with nb of children and adults)
	 * covered by corresponding station
	 * 
	 * @return List of persons (with nb of children and adults)
	 */
	@ParameterizedTest
	@MethodSource("StationNbSource")
	@DisplayName("Get persons covered by station")
	void GetPersonsFromStationNumberTest_ShouldReturnListOfPersonsWithInfos(String stationNumber) {
		// arrange
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

		PersonsInfoDTO piDto = new PersonsInfoDTO();
		// act
		piDto = urlService.getPersonsWithBirthdatesFromFirestations(stationNumber);

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
