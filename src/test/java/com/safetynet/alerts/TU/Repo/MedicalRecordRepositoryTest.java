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
		assertNotNull(mr, "medical record not null");
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

	/**
	 * This method checks getting one medical record for different values
	 */
	@ParameterizedTest
	@MethodSource("MedicalRecordNullSource1")
	@DisplayName("Get one medical record at null")
	void GetOneMedicalRecordNullTest_ShouldReturnNull(String firstName, String lastName) {
		// Arrange

		// Act
		MedicalRecord mr = medicalRecordRepo.readAMedicalRecord(firstName, lastName);

		// Assert
		assertNull(mr, "medical record null");
	}

	// with its data
	private static Stream<Arguments> MedicalRecordNullSource1() {

		String FirstName = null;
		String LastName = null;
		String FirstName1 = "Allison";
		String LastName1 = null;
		String FirstName2 = null;
		String LastName2 = "Boyd";

		return Stream.of(Arguments.of(FirstName, LastName), Arguments.of(FirstName1, LastName1),
				Arguments.of(FirstName2, LastName2));
	}

	/**
	 * This method checks updating medical record for different values
	 */
	@ParameterizedTest
	@MethodSource("UpdateMedicalRecordSource")
	@DisplayName("Update one medical record")
	void UpdateMedRecTest_ShouldReturnMedicalRecord(MedicalRecord medicalRecord) {
		// arrange

		// act
		MedicalRecord medRec = medicalRecordRepo.updateAMedicalRecord(medicalRecord);
		// assert
		assertThat(medRec).isNotNull();
	}

	// with its data
	private static Stream<Arguments> UpdateMedicalRecordSource() {

		MedicalRecord mr = new MedicalRecord("Allison", "Boyd", "03/15/1965", new String[] { "aznol:200mg" },
				new String[] { "nillacilan" });
		MedicalRecord mr1 = new MedicalRecord("john", "Boyd", "03/06/1984",
				new String[] { "aznol:350mg", "hydrapermazol:100mg" }, new String[] { "nillacilan" });
		MedicalRecord mr3 = new MedicalRecord("Roger", "Boyd", "09/06/2017", new String[] {}, new String[] {});

		return Stream.of(Arguments.of(mr), Arguments.of(mr1), Arguments.of(mr3));
	}

	/**
	 * This method checks not updating medical record for different values
	 */
	@ParameterizedTest
	@MethodSource("updateNullSource")
	@DisplayName("Not update one medical record == null")
	void UpdateMedicalRecordTest_ShouldReturnNull(MedicalRecord medRecord) {
		// Arrange

		// Act
		MedicalRecord medRec = medicalRecordRepo.updateAMedicalRecord(medRecord);

		// Assert
		assertNull(medRec, "medical record is completely null");
	}

	// with its data
	private static Stream<Arguments> updateNullSource() {

		MedicalRecord medRecord = null;
		// this medical record already exists
		MedicalRecord medRecord2 = new MedicalRecord("allison", "Bo", "03/15/1965", new String[] { "aznol:200mg" },
				new String[] { "nillacilan" });
		return Stream.of(Arguments.of(medRecord), Arguments.of(medRecord2), null);
	}

	/**
	 * This method checks deleting medical record for different values
	 */
	@ParameterizedTest
	@MethodSource("DeleteMedicalRecordSource")
	@DisplayName("Delete one medical record")
	void DeleteMedRecTest_ShouldReturnMedicalRecord(MedicalRecord medicalRecord) {
		// arrange

		// act
		Boolean ret = medicalRecordRepo.deleteAMedicalRecord(medicalRecord);
		// assert
		assertThat(ret).isTrue();
	}

	// with its data
	private static Stream<Arguments> DeleteMedicalRecordSource() {

		MedicalRecord mr = new MedicalRecord("Allison", "Boyd", "03/15/1965", new String[] { "aznol:200mg" },
				new String[] { "nillacilan" });
		MedicalRecord mr1 = new MedicalRecord("john", "Boyd", "03/06/1984",
				new String[] { "aznol:350mg", "hydrapermazol:100mg" }, new String[] { "nillacilan" });
		MedicalRecord mr3 = new MedicalRecord("Roger", "Boyd", "09/06/2017", new String[] {}, new String[] {});

		return Stream.of(Arguments.of(mr), Arguments.of(mr1), Arguments.of(mr3));
	}

	/**
	 * This method checks not deleting medical record for different values
	 */
	@ParameterizedTest
	@MethodSource("deleteNullSource")
	@DisplayName("Not delete one medical record == null")
	void DeleteMedicalRecordTest_ShouldReturnNull(MedicalRecord medRecord) {
		// Arrange

		// Act
		Boolean ret = medicalRecordRepo.deleteAMedicalRecord(medRecord);
		// assert
		assertThat(ret).isFalse();
	}

	// with its data
	private static Stream<Arguments> deleteNullSource() {

		MedicalRecord medRecord = null;
		// this medical record already exists
		MedicalRecord medRecord2 = new MedicalRecord("allison", "Bo", "03/15/1965", new String[] { "aznol:200mg" },
				new String[] { "nillacilan" });
		return Stream.of(Arguments.of(medRecord), Arguments.of(medRecord2), null);
	}
}
