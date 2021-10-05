package com.safetynet.alerts.TI;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.repository.IPersonRepository;
import com.safetynet.alerts.service.PersonService;

class PersonServiceTest {

	// To be tested
	private static PersonService personService;

	// to be mocked
	@Mock
	private static IPersonRepository personRepository;

	@BeforeAll
	private static void setUp() {

		personService = new PersonService();
	}

	@BeforeEach
	private void setUpPerTest() {

	}

	/**
	 * This method checks PersonDTO return for different values
	 */
	@ParameterizedTest
	@MethodSource("PersonDTOSource")
	void AddPersonTest_ShouldReturnPersonDTO(PersonDTO personDTO) {

		PersonDTO pDTO = personService.addPerson(personDTO);
		assertEquals("Jojo", pDTO.getFirstName());
		assertEquals("Goupil", pDTO.getFirstName());
	}

	// with its data
	private static Stream<Arguments> PersonDTOSource() {
		PersonDTO personDTO = new PersonDTO();
		personDTO.setFirstName("Jojo");
		personDTO.setLastName("Lapin");
		personDTO.setAddress("5 rue du terrier");
		personDTO.setCity("Laplaine");
		personDTO.setZip("123456");
		personDTO.setPhone("9876543210");
		personDTO.setEmail("jojo@lapin.com");

		PersonDTO personDTO2 = new PersonDTO();
		personDTO2.setFirstName("Goupil");
		personDTO2.setLastName("Renard");
		personDTO2.setAddress("5 rue de la taniere");
		personDTO2.setCity("Laforet");
		personDTO2.setZip("654321");
		personDTO2.setPhone("0123456789");
		personDTO2.setEmail("goupil@renard.com");

		return Stream.of(Arguments.of(personDTO), Arguments.of(personDTO2));
	}
}
