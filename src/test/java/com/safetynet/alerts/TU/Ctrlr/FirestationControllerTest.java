package com.safetynet.alerts.TU.Ctrlr;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.FirestationController;
import com.safetynet.alerts.dto.FirestationDTO;
import com.safetynet.alerts.service.IFirestationService;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = { FirestationController.class })
class FirestationControllerTest {
//	@Autowired
//	private WebApplicationContext webApplicationContext;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IFirestationService firestationService;

	@BeforeEach
	void SetUpApplicationContext() {
//		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@DisplayName("findAllFirestations")
	void whenValidInputFindAllF_thenReturnsListOfFirestation() throws Exception {
		// ARRANGE
		FirestationDTO fd1 = new FirestationDTO("123 route de Paris", "3");
		List<FirestationDTO> lfd = new ArrayList<>();
		lfd.add(fd1);
		when(firestationService.getAllFirestations()).thenReturn(lfd);

		// ACT
		MvcResult result = mockMvc.perform(get("/firestations").contentType("application/json")).andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		// ASSERT
		assertThat(contentAsString).isNotNull();
	}

	@Test
	@DisplayName("findAllFirestations at null")
	void whenValidInputFindAllFAtNull_thenReturnsNull() throws Exception {
		// ARRANGE
		List<FirestationDTO> lfd = null;
		when(firestationService.getAllFirestations()).thenReturn(lfd);

		// ACT
		MvcResult result = mockMvc.perform(get("/firestations").contentType("application/json")).andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		// ASSERT
		assertThat(contentAsString).isNullOrEmpty();
	}

	@Test
	@DisplayName("getOneFirestation")
	void whenValidInputFindOneF_thenReturns302() throws Exception {
		// ARRANGE
		String station = "2";
		String address = "1234 route de Lyon";
		FirestationDTO fd1 = new FirestationDTO("123 route de Paris", "3");
		when(firestationService.getOneFirestation(station, address)).thenReturn(fd1);
		// ACT
		MvcResult result = mockMvc.perform(get("/firestation").param("station", "2").contentType("application/json"))
				.andReturn();

		String contentAsString = result.getResponse().getContentAsString();

		// ASSERT
		assertThat(contentAsString).isNotNull();

	}

	@Test
	@DisplayName("getOneFirestation at null")
	void whenValidInputFindOneFAtNull_thenReturnsNull() throws Exception {
		// ARRANGE
		String station = "2";
		String address = "1234 route de Lyon";
		FirestationDTO fd1 = new FirestationDTO();
		when(firestationService.getOneFirestation(station, address)).thenReturn(fd1);
		// ACT
		MvcResult result = mockMvc.perform(get("/firestation").param("station", "2").contentType("application/json"))
				.andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		// ASSERT
		assertThat(contentAsString).isNullOrEmpty();

	}

	@Test
	@DisplayName("updateOneFirestation")
	void whenValidInputUpdateOneFirestation_thenReturns302() throws Exception {
		// ARRANGE
		FirestationDTO fd1 = new FirestationDTO("892 Downing Ct", "1");
		FirestationDTO fd1RequestBody = new FirestationDTO("987 Downing St", "4");

		when(firestationService.updateOneFirestation(Mockito.any(FirestationDTO.class))).thenReturn(fd1);
		// ACT
		ObjectMapper objectMapper = new ObjectMapper();
		String fd1IntoString = objectMapper.writeValueAsString(fd1RequestBody);
		MvcResult result = mockMvc.perform(put("/firestation/update").param("firestationDTO", fd1IntoString)
				.content(fd1IntoString).contentType("application/json")).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		// ASSERT
		assertThat(contentAsString).isNotNull();
	}

	@Test
	@DisplayName("updateOneFirestation at null")
	void whenValidInputUpdateOneFirestationAtNull_thenReturnsNull() throws Exception {
		// ARRANGE
		FirestationDTO fd1 = null;
		FirestationDTO fd1RequestBody = new FirestationDTO("987 Downing St", "4");

		when(firestationService.updateOneFirestation(Mockito.any(FirestationDTO.class))).thenReturn(fd1);
		// ACT
		ObjectMapper objectMapper = new ObjectMapper();
		String fd1IntoString = objectMapper.writeValueAsString(fd1RequestBody);
		MvcResult result = mockMvc.perform(put("/firestation/update").param("firestationDTO", fd1IntoString)
				.content(fd1IntoString).contentType("application/json")).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		// ASSERT
		assertThat(contentAsString).isNullOrEmpty();
	}

	@Test
	@DisplayName("createOneFirestation")
	void whenValidInputCreateOneFirestation_thenReturns302() throws Exception {

		// ARRANGE

		FirestationDTO fd1 = new FirestationDTO("123 route de paris", "5");
		when(firestationService.addFirestation(Mockito.any(FirestationDTO.class))).thenReturn(fd1);
		// ACT
		ObjectMapper objectMapper = new ObjectMapper();
		String fd1IntoString = objectMapper.writeValueAsString(fd1);
		MvcResult result = mockMvc
				.perform(post("/firestation/create").content(fd1IntoString).contentType("application/json"))
				.andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		// ASSERT
		assertThat(contentAsString).isNotNull();
	}

	@Test
	@DisplayName("createOneFirestation at null")
	void whenValidInputCreateOneFirestationAtNull_thenReturnsNull() throws Exception {
		// ARRANGE
		FirestationDTO fd2 = null;
		FirestationDTO fd1 = new FirestationDTO();
		when(firestationService.addFirestation(Mockito.any(FirestationDTO.class))).thenReturn(fd2);
		// ACT
		ObjectMapper objectMapper = new ObjectMapper();
		String fd1IntoString = objectMapper.writeValueAsString(fd1);
		MvcResult result = mockMvc
				.perform(post("/firestation/create").content(fd1IntoString).contentType("application/json"))
				.andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		// ASSERT
		assertThat(contentAsString.contains("Firestation already created."));
	}

	@Test
	@DisplayName("deleteOneFirestation")
	void whenValidInputDeleteOneF_thenReturns302() throws Exception {

		// ARRANGE
		String station = "3";
		String address = "12 rue de la taniere";
		FirestationDTO fd1 = new FirestationDTO("123 route de paris", "5");
		when(firestationService.getOneFirestation(station, address)).thenReturn(fd1);
		when(firestationService.deleteOneFirestation(Mockito.any(FirestationDTO.class))).thenReturn(Boolean.TRUE);

		// ACT
		MvcResult result = mockMvc.perform(delete("/firestation/delete").param("address", "1509 Culver St")
				.param("station", "3").contentType("application/json")).andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		// ASSERT
		assertThat(contentAsString).isNullOrEmpty();
	}

	@Test
	@DisplayName("deleteOneFirestation at null")
	void whenValidInputDeleteOneFAtNull_thenReturnsNull() throws Exception {

		// ARRANGE
		String station = "3";
		String address = "12 rue de la taniere";
		FirestationDTO fd1 = new FirestationDTO();
		when(firestationService.getOneFirestation(station, address)).thenReturn(fd1);
		when(firestationService.deleteOneFirestation(Mockito.any(FirestationDTO.class))).thenReturn(false);

		// ACT
		MvcResult result = mockMvc.perform(delete("/firestation/delete").param("address", "1509 Culver St")
				.param("station", "3").contentType("application/json")).andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		// ASSERT
		assertThat(contentAsString).isNullOrEmpty();
	}
}
