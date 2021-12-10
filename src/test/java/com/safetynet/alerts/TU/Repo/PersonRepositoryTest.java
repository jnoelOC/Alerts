package com.safetynet.alerts.TU.Repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.IPersonRepository;
import com.safetynet.alerts.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
class PersonRepositoryTest {

	// To be tested
	private IPersonRepository personRepo;

	@BeforeEach
	private void setUpPerTest() {
		personRepo = new PersonRepository();
		// To populate personRepository
		personRepo.save(new Person("Lou", "Doillon", "tutu", "titi", "tete", "tyty", "tata"));
		personRepo.save(new Person("isabelle", "adjani", "tutu", "Culver", "tete", "tyty", "tata"));
		personRepo.save(new Person("catherine", "deneuve", "tutu", "Lyon", "tete", "tyty", "tata"));
	}

	/**
	 * This method checks finding all Persons
	 */
	@Test
	@DisplayName("Find all persons")
	void FindAllPersonTest_ShouldReturnListOfPerson() {
		// arrange
		List<Person> lp = new ArrayList<>();
		// act
		lp = personRepo.findAllPersons();
		// assert
		assertNotNull(lp, "list of persons not null");
	}

	/**
	 * This method checks saving Person for different values
	 */
	@ParameterizedTest
	@MethodSource("PersonSource")
	@DisplayName("Save one person")
	void SavePersonTest_ShouldReturnPerson(Person person) {
		// arrange
		personRepo = new PersonRepository(); // this line to reinitialize personRepo

		personRepo.save(person);
		// act
		List<Person> lp = personRepo.findAllPersons();
		// assert
		assertThat(person).isEqualTo(lp.get(0));
	}

	// with its data
	private static Stream<Arguments> PersonSource() {
		Person person = new Person("Jojo", "Lapin", "5 rue du terrier", "Laplaine", "123456", "9876543210",
				"jojo@lapin.com");
		Person person2 = new Person("Goupil", "Renard", "5 rue de la taniere", "Laforet", "654321", "0123456789",
				"goupil@renard.com");
		Person person3 = new Person("Ysengrin", "Loup", "15 rue du trou", "Lebois", "564789", "3214567890",
				"ysengrin@loup.com");
		Person person4 = new Person("Jojoie", "Lapin", "5 rue du terrier", "Laplaine", "123456", "9876543210",
				"jojo@lapin.com");
		Person person5 = new Person("", "", "", "", "", "", "");

		return Stream.of(Arguments.of(person), Arguments.of(person2), Arguments.of(person3), Arguments.of(person4),
				Arguments.of(person5));
	}

	/**
	 * This method checks not saving Person for different values
	 */
	@ParameterizedTest
	@MethodSource("NullSource")
	@DisplayName("Not save one person == null")
	void SavePersonTest_ShouldReturnNull(Person person1) {
		// Arrange

		// Act
		Person p = personRepo.save(person1);

		// Assert
		assertNull(p, "person is completely null");
	}

	// with its data
	private static Stream<Arguments> NullSource() {
		Person person = null;

		// this person already exists
		Person person5 = new Person("isabelle", "adjani", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");

		return Stream.of(Arguments.of(person5), Arguments.of(person), null);
	}

	/**
	 * This method checks getting one Person for different values
	 */
	@ParameterizedTest
	@MethodSource("PersonSource1")
	@DisplayName("Get one person")
	void GetOnePersonTest_ShouldReturnPerson(String name1, String name2) {
		// Arrange

		// Act
		Person p = personRepo.readAPerson(name1, name2);

		// Assert
		assertNotNull(p, "person not null");
	}

	// with its data
	private static Stream<Arguments> PersonSource1() {

		String FirstName = "isabelle";
		String LastName = "adjani";
		String FirstName1 = "catherine";
		String LastName1 = "deneuve";
		String FirstName2 = "lou";
		String LastName2 = "doillon";

		return Stream.of(Arguments.of(FirstName, LastName), Arguments.of(FirstName1, LastName1),
				Arguments.of(FirstName2, LastName2));
	}

	/**
	 * This method checks getting one Person for different values
	 */
	@ParameterizedTest
	@MethodSource("PersonNullSource1")
	@DisplayName("Get one person == null")
	void GetOnePersonTest_ShouldReturnNull(String name1, String name2) {
		// Arrange

		// Act
		Person p = personRepo.readAPerson(name1, name2);

		// Assert
		assertNull(p, "person null");
	}

	// with its data
	private static Stream<Arguments> PersonNullSource1() {

		String FirstName = "isabel";
		String LastName = "adjani";
		String FirstName1 = "catherine";
		String LastName1 = "dene";
		String FirstName2 = "lo";
		String LastName2 = "doi";

		return Stream.of(Arguments.of(FirstName, LastName), Arguments.of(FirstName1, LastName1),
				Arguments.of(FirstName2, LastName2), Arguments.of(null, null), Arguments.of(FirstName1, null),
				Arguments.of(null, LastName));
	}

	/**
	 * This method checks deleting a Person for different values
	 */
	@ParameterizedTest
	@MethodSource("DeleteSource")
	@DisplayName("delete one person")
	void deletePersonTest_ShouldReturnTrue(Person person1) {
		// Arrange

		// Act
		Boolean ret = personRepo.deleteAPerson(person1);

		// Assert
		assertTrue(ret);
	}

	// with its data
	private static Stream<Arguments> DeleteSource() {
		Person person3 = new Person("Lou", "Doillon", "tutu", "titi", "tete", "tyty", "tata");
		Person person4 = new Person("isabelle", "adjani", "tutu", "Culver", "tete", "tyty", "tata");
		Person person5 = new Person("catherine", "deneuve", "tutu", "Lyon", "tete", "tyty", "tata");

		return Stream.of(Arguments.of(person3), Arguments.of(person4), Arguments.of(person5));
	}

	/**
	 * This method checks deleting a Person null for different values
	 */
	@ParameterizedTest
	@MethodSource("DeleteNullSource")
	@DisplayName("delete one person == null")
	void deletePersonTest_ShouldReturnFalse(Person person1) {
		// Arrange

		// Act
		Boolean ret = personRepo.deleteAPerson(person1);

		// Assert
		assertFalse(ret);
	}

	// with its data
	private static Stream<Arguments> DeleteNullSource() {
		Person p = null;
		Person person3 = new Person("Lou", "Doil", "tutu", "titi", "tete", "tyty", "tata");
		Person person4 = new Person("isabel", "adjani", "tutu", "Culver", "tete", "tyty", "tata");
		Person person5 = new Person("cather", "den", "tutu", "Lyon", "tete", "tyty", "tata");

		return Stream.of(Arguments.of(p), Arguments.of(person3), Arguments.of(person4), Arguments.of(person5));
	}

	/**
	 * This method checks updating a Person for different values
	 */
	@ParameterizedTest
	@MethodSource("UpdateSource")
	@DisplayName("update one person")
	void updatePersonTest_ShouldReturnTrue(Person person1) {
		// Arrange

		// Act
		Person p = personRepo.updateAPerson(person1);

		// Assert
		assertNotNull(p, "person updated");
	}

	// with its data
	private static Stream<Arguments> UpdateSource() {
		Person person3 = new Person("Lou", "doillon", "5 rue st sulpice", "paris", "tete", "tyty", "tata");
		Person person4 = new Person("isabelle", "adjani", "place du prado", "marseille", "tete", "tyty", "tata");
		Person person5 = new Person("catherine", "deneuve", "12 rue de la garonne", "bordeaux", "tete", "tyty", "tata");

		return Stream.of(Arguments.of(person3), Arguments.of(person4), Arguments.of(person5));
	}

	/**
	 * This method checks updating a Person null for different values
	 */
	@ParameterizedTest
	@MethodSource("UpdateNullSource")
	@DisplayName("update one person == null")
	void updatePersonTest_ShouldReturnNull(Person person1) {
		// Arrange

		// Act
		Person p = personRepo.updateAPerson(person1);

		// Assert
		assertNull(p, "person null");
	}

	// with its data
	private static Stream<Arguments> UpdateNullSource() {
		Person p = null;
		Person person3 = new Person("Lou", "doil", "5 rue st sulpice", "paris", "tete", "tyty", "tata");
		Person person4 = new Person("isa", "adjani", "place du prado", "marseille", "tete", "tyty", "tata");

		return Stream.of(Arguments.of(p), Arguments.of(person3), Arguments.of(person4));
	}

	/**
	 * This method checks getting emails from city for different values
	 */
	@ParameterizedTest
	@MethodSource("emailsSource1")
	@DisplayName("Get emails from city")
	void GetEmailsTest_ShouldReturnPerson(String city) {
		// Arrange

		// Act
		List<String> ls = personRepo.getEmailsFrom(city);

		// Assert
		assertNotNull(ls, "list of emails not null");
	}

	// with its data
	private static Stream<Arguments> emailsSource1() {

		String city1 = "titi";
		String city2 = "Culver";
		String city3 = "Lyon";

		return Stream.of(Arguments.of(city1), Arguments.of(city2), Arguments.of(city3));
	}

	/**
	 * This method checks getting list of emails at null for different values
	 */
	@ParameterizedTest
	@MethodSource("ListEmailsNullSource1")
	@DisplayName("Get list of emails == null")
	void GetEmailsTest_ShouldReturnNull(String city) {
		// Arrange

		// Act
		List<String> ls = personRepo.getEmailsFrom(city);

		// Assert
		assertNotNull(ls, "list of emails empty");
	}

	// with its data
	private static Stream<Arguments> ListEmailsNullSource1() {

		String city3 = null;
		return Stream.of(Arguments.of(city3));
	}
}
