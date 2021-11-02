package com.safetynet.alerts.TU;

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

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.IMedicalRecordRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;

class MedicalRecordRepositoryTest {
	// To be tested
	private IMedicalRecordRepository medicalRecordRepo;

	@BeforeEach
	private void setUpPerTest() {
		medicalRecordRepo = new MedicalRecordRepository();

		medicalRecordRepo.save(
				new MedicalRecord("John", "Boyd", "03/06/1984", new String[] { "aznol:350mg", "hydrapermazol:100mg" },
						new String[] { "peanut", "shellfish", "aznol", "butter" }));
		medicalRecordRepo.save(new MedicalRecord("Allison", "Boyd", "03/15/1965", new String[] { "aznol:200mg" },
				new String[] { "nillacilan" }));
		medicalRecordRepo.save(new MedicalRecord("Roger", "Boyd", "09/06/2017", new String[] {}, new String[] {}));

	}

	/**
	 * This method checks finding all medical records
	 */
	@Test
	@DisplayName("Find all medical records")
	void FindAllMedicalRecordsTest_ShouldReturnListOfMedicalRecords() {
		// arrange
		List<MedicalRecord> lmr = new ArrayList<>();
		// act
		lmr = medicalRecordRepo.findAllMedicalRecords();
		// assert
		assertNotNull(lmr, "list of medical reocrds not null");
	}

	/**
	 * This method checks saving medical record for different values
	 */
	@ParameterizedTest
	@MethodSource("MedicalRecordSource")
	@DisplayName("Save one medical record")
	void SaveMedRecTest_ShouldReturnMedicalRecord(MedicalRecord medicalRecord) {
		// arrange
		medicalRecordRepo = new MedicalRecordRepository(); // this line to reinitialize firestationRepo

		medicalRecordRepo.save(medicalRecord);
		// act
		List<MedicalRecord> lmr = medicalRecordRepo.findAllMedicalRecords();
		// assert
		assertThat(medicalRecord).isEqualTo(lmr.get(0));
	}

	// with its data
	private static Stream<Arguments> MedicalRecordSource() {

		MedicalRecord mr = new MedicalRecord("Allison", "Boyd", "03/15/1965", new String[] { "aznol:200mg" },
				new String[] { "nillacilan" });
		MedicalRecord mr1 = new MedicalRecord("john", "Boyd", "03/06/1984",
				new String[] { "aznol:350mg", "hydrapermazol:100mg" }, new String[] { "nillacilan" });
		MedicalRecord mr2 = new MedicalRecord("Tenley", "Boyd", "02/18/2012", new String[] {},
				new String[] { "peanut" });
		MedicalRecord mr3 = new MedicalRecord("Roger", "Boyd", "09/06/2017", new String[] {}, new String[] {});

		return Stream.of(Arguments.of(mr), Arguments.of(mr1), Arguments.of(mr2), Arguments.of(mr3));
	}

	/**
	 * This method checks not saving medical record for different values
	 */
	@ParameterizedTest
	@MethodSource("NullSource")
	@DisplayName("Not save one medical record == null")
	void SaveMedicalRecordTest_ShouldReturnNull(MedicalRecord medRec) {
		// Arrange

		// Act
		MedicalRecord mr = medicalRecordRepo.save(medRec);

		// Assert
		assertNull(mr, "medical record is completely null");
	}

	// with its data
	private static Stream<Arguments> NullSource() {
		MedicalRecord medRecord = null;

		// this medical record already exists
		MedicalRecord medRecord2 = new MedicalRecord("allison", "Boyd", "03/15/1965", new String[] { "aznol:200mg" },
				new String[] { "nillacilan" });
		return Stream.of(Arguments.of(medRecord), Arguments.of(medRecord2), null);
	}

	/**
	 * This method checks getting one medical record for different values
	 */
	@ParameterizedTest
	@MethodSource("MedicalRecordSource1")
	@DisplayName("Get one medical record")
	void GetOneMedicalRecordTest_ShouldReturnMedicalRecord(String firstName, String lastName) {
		// Arrange

		// Act
		MedicalRecord mr = medicalRecordRepo.readAMedicalRecord(firstName, lastName);

		// Assert
		assertNotNull(mr, "firestation not null");
	}

	// with its data
	private static Stream<Arguments> MedicalRecordSource1() {

		String FirstName = "john";
		String LastName = "boyd";
		String FirstName1 = "Allison";
		String LastName1 = "Boyd";
		String FirstName2 = "roger";
		String LastName2 = "Boyd";

		return Stream.of(Arguments.of(FirstName, LastName), Arguments.of(FirstName1, LastName1),
				Arguments.of(FirstName2, LastName2));
	}

}
