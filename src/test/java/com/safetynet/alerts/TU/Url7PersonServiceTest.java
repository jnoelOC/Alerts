package com.safetynet.alerts.TU;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.IPersonRepository;
import com.safetynet.alerts.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
class Url7PersonServiceTest {
	// To be tested
	private IPersonRepository personRepo;

	@BeforeAll
	private static void setUp() {

	}

	@BeforeEach
	private void setUpPerTest() {
		personRepo = new PersonRepository();
		personRepo.save(new Person("Cary", "Grant", "tutu", "titi", "tete", "tyty", "tata"));
		personRepo.save(new Person("James", "Stewart", "tutu", "Culver", "tete", "tyty", "tata"));
		personRepo.save(new Person("Gary", "Cooper", "tutu", "Lyon", "tete", "tyty", "tata"));

	}

	// URL7
	/**
	 * This method checks getting emails of all Persons from a city
	 */
	@ParameterizedTest
	@MethodSource("CitySource")
	@DisplayName("Get emails of persons from the same city")
	void GetEmailsOfPersonsFromCityTest_ShouldReturnListOfEmails(String city) {
		// arrange
		List<String> ls = new ArrayList<>();

		// act
		ls = personRepo.getEmailsFrom(city);
		// assert
		assertNotNull(ls, "list of Emails not null");
	}

	// with its data
	private static Stream<Arguments> CitySource() {
		String city1 = "Culver";
		String city2 = "Lyon";
		return Stream.of(Arguments.of(city1), Arguments.of(city2));
	}

	/**
	 * This method checks getting emails of all Persons from a city
	 */
	@ParameterizedTest
	@MethodSource("CityNullSource")
	@DisplayName("Get none email of persons from city == null")
	void GetEmailsOfPersonsFromNullCityTest_ShouldReturnNull(String city) {
		// arrange
		List<String> ls = new ArrayList<>();

		// act
		ls = personRepo.getEmailsFrom(city);
		// assert
		assertThat(ls).isNullOrEmpty();
	}

	// with its data
	private static Stream<Arguments> CityNullSource() {
		String city1 = "";
		String city3 = null;
		return Stream.of(Arguments.of(city1), null, Arguments.of(city3));
	}
}
