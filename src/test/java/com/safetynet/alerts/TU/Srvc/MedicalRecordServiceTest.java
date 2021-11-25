package com.safetynet.alerts.TU.Srvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.service.MedicalRecordService;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceTest {
	// To be tested
	@InjectMocks
	private MedicalRecordService medicalRecordService;

	@Mock
	private MedicalRecordRepository medicalRecordRepository;

	/**
	 * This method checks getting a MedicalRecordDTO
	 */
	@Test
	@DisplayName("get a MedicalRecordDTO")
	void GetAMedicalRecordTest_ShouldReturnFirestationDTO() {
		// Arrange
		MedicalRecord medicalRecord = new MedicalRecord("Jojo", "Lapin", "12/12/2002",
				new String[] { "aspirine", "doliprane" }, new String[] { "gluten" });
		MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO("loulou", "ysengrin", "02/02/1999",
				new String[] { "aspirine" }, new String[] {});
		when(medicalRecordRepository.readAMedicalRecord(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(medicalRecord);

		// Act
		medicalRecordDTO = medicalRecordService.getOneMedicalRecord("jojo", "Lapin");
		// Assert
		assertNotNull(medicalRecordDTO, "firestationDTO not null.");
		assertEquals("Jojo", medicalRecordDTO.getFirstName());
	}

	/**
	 * This method checks deleting a mediaclRecordDTO
	 */
	@Test
	@DisplayName("delete a medicalRecordDTO")
	void DeleteAMedicalRecordTest_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO("loulou", "ysengrin", "02/02/1999",
				new String[] { "aspirine" }, new String[] {});
		when(medicalRecordRepository.deleteAMedicalRecord(Mockito.any(MedicalRecord.class))).thenReturn(true);

		// Act
		ret = medicalRecordService.deleteOneMedicalRecord(medicalRecordDTO);
		// Assert
		assertTrue(ret);
	}

	/**
	 * This method checks finding All medicalRecordDTO
	 */
	@Test
	@DisplayName("find all medicalRecordsDTO")
	void FindAllMedicalRecordTest_ShouldReturnListOfMedicalRecordDTO() {
		// Arrange
		List<MedicalRecordDTO> lpDTO = null;

		List<MedicalRecord> listePasDto = new ArrayList<>();
		listePasDto.add(new MedicalRecord("jojo", "lapin", "12/12/2002", new String[] { "doliprane" },
				new String[] { "gluten" }));
		listePasDto.add(
				new MedicalRecord("loulou", "ysengrin", "02/02/1999", new String[] { "aspirine" }, new String[] {}));

		when(medicalRecordRepository.findAllMedicalRecords()).thenReturn(listePasDto);
		// Act
		lpDTO = medicalRecordService.getAllMedicalRecords();
		// Assert
		assertNotNull(lpDTO, "list not null.");
	}

	/**
	 * This method checks adding a medicalRecordDTO
	 */
	@ParameterizedTest
	@MethodSource("MedicalRecordDTOSource")
	@DisplayName("Add a medicalRecordDTO")
	void AddAMedicalRecordTest_ShouldReturnMedicalRecordDTO(MedicalRecordDTO medicalRecordDTO) {
		// Arrange
		MedicalRecord medicalRecord = new MedicalRecord("jojo", "lapin", "12/12/2002", new String[] { "doliprane" },
				new String[] { "gluten" });
		when(medicalRecordRepository.save(Mockito.any(MedicalRecord.class))).thenReturn(medicalRecord);
		// Act
		MedicalRecordDTO mrDTO = medicalRecordService.addMedicalRecord(medicalRecordDTO);
		// Assert
		assertNotNull(mrDTO, "medicalRecord not null.");
	}

	// with its data
	private static Stream<Arguments> MedicalRecordDTOSource() {

		MedicalRecordDTO medicalRecordDTO1 = new MedicalRecordDTO("loulou", "ysengrin", "02/02/1999",
				new String[] { "aspirine" }, new String[] {});
		MedicalRecordDTO medicalRecordDTO2 = new MedicalRecordDTO("goupil", "renard", "12/12/2002",
				new String[] { "doliprane" }, new String[] { "gluten" });

		return Stream.of(Arguments.of(medicalRecordDTO1), Arguments.of(medicalRecordDTO2));
	}

	/**
	 * This method checks updating a MedicalRecordDTO
	 */
	@Test
	@DisplayName("update a medicalRecordDTO")
	void UpdateAMedicalRecordTest_ShouldReturnMedicalRecordDTO() {
		// Arrange
		MedicalRecord medicalRecord = new MedicalRecord("jojo", "lapin", "12/12/2002", new String[] { "doliprane" },
				new String[] { "gluten" });
		MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO("jojo", "lapin", "02/02/2002",
				new String[] { "efferalgan" }, new String[] { "iode" });

		when(medicalRecordRepository.updateAMedicalRecord(Mockito.any(MedicalRecord.class))).thenReturn(medicalRecord);

		// Act
		medicalRecordDTO = medicalRecordService.updateOneMedicalRecord(medicalRecordDTO);
		// Assert
		assertNotNull(medicalRecordDTO, "MedicalRecordDTO not null");
	}

}
