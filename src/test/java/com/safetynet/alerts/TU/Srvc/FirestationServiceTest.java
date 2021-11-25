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

import com.safetynet.alerts.dto.FirestationDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.FirestationRepository;
import com.safetynet.alerts.service.FirestationService;

@ExtendWith(MockitoExtension.class)
class FirestationServiceTest {
	// To be tested
	@InjectMocks
	private FirestationService firestationService;

	@Mock
	private FirestationRepository firestationRepository;

	/**
	 * This method checks getting a firestationDTO
	 */
	@Test
	@DisplayName("get a firestationDTO")
	void GetAFirestationTest_ShouldReturnFirestationDTO() {
		// Arrange
		FirestationDTO firestationDTO = null;
		Firestation firestation = new Firestation("laville", "1");
		when(firestationRepository.readAFirestation(Mockito.anyString(), Mockito.anyString())).thenReturn(firestation);

		// Act
		firestationDTO = firestationService.getOneFirestation("laplaine", "2");
		// Assert
		assertNotNull(firestationDTO, "firestationDTO not null.");
	}

	/**
	 * This method checks deleting a firestationDTO
	 */
	@Test
	@DisplayName("delete a firestationDTO")
	void DeleteAFirestationTest_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		FirestationDTO firestationDTO = new FirestationDTO("Laplaine", "1");
		when(firestationRepository.deleteAFirestation(Mockito.any(Firestation.class))).thenReturn(true);

		// Act
		ret = firestationService.deleteOneFirestation(firestationDTO);
		// Assert
		assertTrue(ret);
	}

	/**
	 * This method checks finding All PersonsDTO
	 */
	@Test
	@DisplayName("find all firestationsDTO")
	void FindAllFirestationsTest_ShouldReturnListOfFirestationsDTO() {
		// Arrange
		List<Firestation> listePasDto = new ArrayList<>();
		listePasDto.add(new Firestation("5 rue de l'atre", "1"));
		listePasDto.add(new Firestation("5 rue de la taniere", "2"));
		listePasDto.add(new Firestation("5 rue du cirque", "3"));

		when(firestationRepository.findAllFirestations()).thenReturn(listePasDto);

		List<FirestationDTO> lpDTO = null;

		// Act
		lpDTO = firestationService.getAllFirestations();
		// Assert
		assertNotNull(lpDTO, "list not null.");
	}

	/**
	 * This method checks adding a firestationDTO
	 */
	@ParameterizedTest
	@MethodSource("FirestationDTOSource")
	@DisplayName("Add a firestationDTO")
	void AddAFirestationTest_ShouldReturnFirestationDTO(FirestationDTO firestationDTO) {
		// Arrange
		Firestation firestation = new Firestation("5 rue de l'atre", "3");
		when(firestationRepository.save(Mockito.any(Firestation.class))).thenReturn(firestation);
		// Act
		FirestationDTO fDTO = firestationService.addFirestation(firestationDTO);
		// Assert
		assertEquals("3", fDTO.getStation());
	}

	// with its data
	private static Stream<Arguments> FirestationDTOSource() {
		FirestationDTO firestationDTO1 = new FirestationDTO("5 rue du terrier", "4");
		FirestationDTO firestationDTO2 = new FirestationDTO("5 rue de la taniere", "2");

		return Stream.of(Arguments.of(firestationDTO1), Arguments.of(firestationDTO2));
	}

	/**
	 * This method checks updating a FirestationDTO
	 */
	@Test
	@DisplayName("update a firestationDTO")
	void UpdateAFirestationTest_ShouldReturnFirestationDTO() {
		// Arrange
		Firestation firestation = new Firestation("5 rue du terrier", "1");
		FirestationDTO firestationDTO = new FirestationDTO("5 rue du terrier", "3");

		when(firestationRepository.updateAFirestation(Mockito.any(Firestation.class))).thenReturn(firestation);

		// Act
		firestationDTO = firestationService.updateOneFirestation(firestationDTO);
		// Assert
		assertNotNull(firestationDTO, "firestationDTO not null");
	}
}
