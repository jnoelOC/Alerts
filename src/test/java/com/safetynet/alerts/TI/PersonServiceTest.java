package com.safetynet.alerts.TI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
	@DisplayName("Add person")
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
	@DisplayName("find persons")
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

}
