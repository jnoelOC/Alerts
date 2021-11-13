package com.safetynet.alerts.TU.Repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
		Person person = new Person();
		person.setFirstName("Jojo");
		person.setLastName("Lapin");
		person.setAddress("5 rue du terrier");
		person.setCity("Laplaine");
		person.setZip("123456");
		person.setPhone("9876543210");
		person.setEmail("jojo@lapin.com");

		Person person2 = new Person();
		person2.setFirstName("Goupil");
		person2.setLastName("Renard");
		person2.setAddress("5 rue de la taniere");
		person2.setCity("Laforet");
		person2.setZip("654321");
		person2.setPhone("0123456789");
		person2.setEmail("goupil@renard.com");

		Person person3 = new Person();
		person3.setFirstName("Ysengrin");
		person3.setLastName("Loup");
		person3.setAddress("15 rue du trou");
		person3.setCity("Lebois");
		person3.setZip("564789");
		person3.setPhone("3214567890");
		person3.setEmail("ysengrin@loup.com");

		Person person4 = new Person();
		person4.setFirstName("Jojoie");
		person4.setLastName("Lapin");
		person4.setAddress("5 rue du terrier");
		person4.setCity("Laplaine");
		person4.setZip("123456");
		person4.setPhone("9876543210");
		person4.setEmail("jojo@lapin.com");

		Person person5 = new Person();
		person5.setFirstName("");
		person5.setLastName("");
		person5.setAddress("");
		person5.setCity("");
		person5.setZip("");
		person5.setPhone("");
		person5.setEmail("");

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
		Person person5 = new Person();
		person5.setFirstName("isabelle");
		person5.setLastName("adjani");
		person5.setAddress("1509 Culver St");
		person5.setCity("Culver");
		person5.setZip("97451");
		person5.setPhone("841-874-6512");
		person5.setEmail("jaboyd@email.com");

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

}
