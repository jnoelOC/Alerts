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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.FirestationRepository;
import com.safetynet.alerts.repository.IFirestationRepository;

class FirestationRepositoryTest {
	// To be tested
	private IFirestationRepository firestationRepo;

	@BeforeEach
	private void setUpPerTest() {
		firestationRepo = new FirestationRepository();

		firestationRepo.save(new Firestation("1509 Culver St", "3"));
		firestationRepo.save(new Firestation("29 15th St", "2"));
		firestationRepo.save(new Firestation("834 Binoc Ave", "1"));
		firestationRepo.save(new Firestation("644 Gershwin Cir", "2"));
	}

	/**
	 * This method checks finding all firestations
	 */
	@Test
	@DisplayName("Find all firestations")
	void FindAllFirestationsTest_ShouldReturnListOfFirestations() {
		// arrange
		List<Firestation> lf = new ArrayList<>();
		// act
		lf = firestationRepo.findAllFirestations();
		// assert
		assertNotNull(lf, "list of firestations not null");
	}

	/**
	 * This method checks saving firestation for different values
	 */
	@ParameterizedTest
	@MethodSource("FirestationSource")
	@DisplayName("Save one firestation")
	void SaveFirestationTest_ShouldReturnFirestation(Firestation firestation) {
		// arrange
		firestationRepo = new FirestationRepository(); // this line to reinitialize firestationRepo

		firestationRepo.save(firestation);
		// act
		List<Firestation> lf = firestationRepo.findAllFirestations();
		// assert
		assertThat(firestation).isEqualTo(lf.get(0));
	}

	// with its data
	private static Stream<Arguments> FirestationSource() {

		Firestation firestation = new Firestation("1509 Culver St", "3");
		Firestation firestation1 = new Firestation("29 15th St", "2");
		Firestation firestation2 = new Firestation("834 Binoc Ave", "1");
		Firestation firestation3 = new Firestation("644 Gershwin Cir", "2");
		Firestation firestation4 = new Firestation("748 Townings Dr", "5");
		Firestation firestation5 = new Firestation("112 Steppes Pl", "3");

		return Stream.of(Arguments.of(firestation), Arguments.of(firestation1), Arguments.of(firestation2),
				Arguments.of(firestation3), Arguments.of(firestation4), Arguments.of(firestation5));
	}

	/**
	 * This method checks not saving firestation for different values
	 */
	@ParameterizedTest
	@MethodSource("NullSource")
	@DisplayName("Not save one firestation == null")
	void SaveFirestationTest_ShouldReturnNull(Firestation firestation1) {
		// Arrange

		// Act
		Firestation f = firestationRepo.save(firestation1);

		// Assert
		assertNull(f, "firestation is completely null");
	}

	// with its data
	private static Stream<Arguments> NullSource() {
		Firestation firestation = null;

		// this firestation's address already exists
		Firestation firestation2 = new Firestation("1509 Culver St", "3");

		return Stream.of(Arguments.of(firestation2), Arguments.of(firestation), null);
	}

	/**
	 * This method checks getting one firestation for different values
	 */
	@ParameterizedTest
	@MethodSource("FirestationSource1")
	@DisplayName("Get one firestation")
	void GetOneFirestationTest_ShouldReturnFirestation(String stationAddress, String stationNumber) {
		// Arrange

		// Act
		Firestation f = firestationRepo.readAFirestation(stationNumber, stationAddress);

		// Assert
		assertNotNull(f, "firestation not null");
	}

	// with its data
	private static Stream<Arguments> FirestationSource1() {

		String stationAddress = "1509 Culver St";
		String stationNumber = "3";
		String stationAddress1 = "29 15th St";
		String stationNumber1 = "2";
		String stationAddress2 = "834 Binoc Ave";
		String stationNumber2 = "1";

		return Stream.of(Arguments.of(stationAddress, stationNumber), Arguments.of(stationAddress1, stationNumber1),
				Arguments.of(stationAddress2, stationNumber2));
	}

}
