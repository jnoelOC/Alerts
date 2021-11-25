package com.safetynet.alerts.TU.Srvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
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

import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.service.PersonService;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

	// To be tested
	@InjectMocks
	private PersonService personService;

	@Mock
	private PersonRepository personRepository;

	@BeforeAll
	private static void setUp() {
	}

	@BeforeEach
	private void setUpPerTest() {

	}

	/**
	 * This method checks adding PersonDTO
	 */
	@ParameterizedTest
	@MethodSource("PersonDTOSource")
	@DisplayName("Add a personDTO")
	void AddPersonTest_ShouldReturnPersonDTO(PersonDTO personDTO) {
		// Arrange
		Person person = new Person("loulou", "Ysengrin", "5 rue de l'atre", "Laplaine", "112233", "9876543210",
				"lou@ysengrin.com");
		when(personRepository.save(Mockito.any(Person.class))).thenReturn(person);

		// Act
		PersonDTO pDTO = personService.addPerson(personDTO);
		// Assert
		assertEquals("loulou", pDTO.getFirstName());
	}

	// with its data
	private static Stream<Arguments> PersonDTOSource() {
		PersonDTO personDTO = new PersonDTO("Jojo", "Lapin", "5 rue du terrier", "Laplaine", "123456", "9876543210",
				"jojo@lapin.com");
		PersonDTO personDTO2 = new PersonDTO("Goupil", "Renard", "5 rue de la taniere", "Laforet", "654321",
				"0123456789", "goupil@renard.com");
		return Stream.of(Arguments.of(personDTO), Arguments.of(personDTO2));
	}

	/**
	 * This method checks finding All PersonsDTO
	 */
	@Test
	@DisplayName("find all personsDTO")
	void FindAllPersonsTest_ShouldReturnListOfPersonsDTO() {
		// Arrange
		List<Person> listePasDto = new ArrayList<>();
		listePasDto.add(new Person("loulou", "Ysengrin", "5 rue de l'atre", "Laplaine", "112233", "9876543210",
				"lou@ysengrin.com"));
		listePasDto.add(new Person("Goupil", "Renard", "5 rue de la taniere", "Laforet", "654321", "0123456789",
				"goupil@renard.com"));
		listePasDto.add(new Person("Dumbo", "lelefan", "5 rue du cirque", "laville", "665544", "0123456789",
				"dumb@lelefan.com"));
		when(personRepository.findAllPersons()).thenReturn(listePasDto);

		List<PersonDTO> lpDTO = null;

		// Act
		lpDTO = personService.findAllPersons();
		// Assert
		assertNotNull(lpDTO, "list not null.");
	}

	/**
	 * This method checks deleting a PersonDTO
	 */
	@Test
	@DisplayName("delete a personDTO")
	void DeleteAPersonTest_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		PersonDTO personDTO = new PersonDTO("Jojo", "Lapin", "5 rue du terrier", "Laplaine", "123456", "9876543210",
				"jojo@lapin.com");
		when(personRepository.deleteAPerson(Mockito.any(Person.class))).thenReturn(true);

		// Act
		ret = personService.deleteOnePerson(personDTO);
		// Assert
		assertTrue(ret);
	}

	/**
	 * This method checks updating a PersonDTO
	 */
	@Test
	@DisplayName("update a personDTO")
	void UpdateAPersonTest_ShouldReturnPersonDTO() {
		// Arrange
		Person person = new Person("Jojo", "Lapin", "5 rue du terrier", "Laplaine", "zip3000", "1122334455",
				"jo@lapin.com");
		PersonDTO personDTO = new PersonDTO("Jojo", "Lapin", "5 rue du terrier", "Laplaine", "123456", "9876543210",
				"jojo@lapin.com");
		when(personRepository.updateAPerson(Mockito.any(Person.class))).thenReturn(person);

		// Act
		personDTO = personService.updateOnePerson(personDTO);
		// Assert
		assertNotNull(personDTO, "personDTO not null");
	}

	/**
	 * This method checks getting a PersonDTO
	 */
	@Test
	@DisplayName("get a person")
	void GetAPersonTest_ShouldReturnPersonDTO() {
		// Arrange
		PersonDTO personDTO = null;
		Person person = new Person("loulou", "Ysengrin", "5 rue de l'atre", "Laplaine", "112233", "9876543210",
				"lou@ysengrin.com");
		when(personRepository.readAPerson(Mockito.anyString(), Mockito.anyString())).thenReturn(person);

		// Act
		personDTO = personService.getOnePerson("jojo", "lapin");
		// Assert
		assertNotNull(personDTO, "personDTO not null.");
	}
}
