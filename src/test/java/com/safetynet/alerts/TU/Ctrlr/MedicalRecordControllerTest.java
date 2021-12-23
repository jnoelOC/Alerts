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
import com.safetynet.alerts.controller.MedicalRecordController;
import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.service.IMedicalRecordService;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = { MedicalRecordController.class })
class MedicalRecordControllerTest {
//	@Autowired
//	private WebApplicationContext webApplicationContext;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IMedicalRecordService medicalRecordService;

	@BeforeEach
	void SetUpApplicationContext() {
//		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@DisplayName("findAllMedicalRecords")
	void whenValidInputFindAllMR_thenReturnsListOfString() throws Exception {
		// ARRANGE
		MedicalRecordDTO mr1 = new MedicalRecordDTO("John", "Boyd", "03/03/1984",
				new String[] { "aspirine", "doliprane" }, new String[] { "amidon", "gluten" });
		MedicalRecordDTO mr2 = new MedicalRecordDTO("Roger", "Boyd", "04/04/2015", new String[] { "aspirine" },
				new String[] { "farine de blé" });
		List<MedicalRecordDTO> lmrd = new ArrayList<MedicalRecordDTO>();
		lmrd.add(mr1);
		lmrd.add(mr2);

		when(medicalRecordService.getAllMedicalRecords()).thenReturn(lmrd);
		// ACT
		MvcResult result = mockMvc.perform(get("/medicalrecords").contentType("application/json")).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		// ASSERT
		assertThat(contentAsString).isNotNull();
	}

	@Test
	@DisplayName("findAllMedicalRecords at null")
	void whenValidInputFindAllMRAtNull_thenReturnsNull() throws Exception {
		// ARRANGE
		List<MedicalRecordDTO> lmrd = new ArrayList<MedicalRecordDTO>();

		when(medicalRecordService.getAllMedicalRecords()).thenReturn(lmrd);
		// ACT
		MvcResult result = mockMvc.perform(get("/medicalrecords").contentType("application/json")).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		// ASSERT
		assertThat(contentAsString.contains("MedicalRecords not found."));

	}

	@Test
	@DisplayName("getOneMedicalRecord")
	void whenValidInputFindOneMR_thenReturns302() throws Exception {
		// ARRANGE
		String firstName = "Jean";
		String lastName = "Bois";
		MedicalRecordDTO mr1 = new MedicalRecordDTO("John", "Boyd", "03/03/1984",
				new String[] { "aspirine", "doliprane" }, new String[] { "amidon", "gluten" });

		when(medicalRecordService.getOneMedicalRecord(firstName, lastName)).thenReturn(mr1);
		// ACT
		MvcResult result = mockMvc.perform(get("/medicalrecord").param("firstName", "Jacob").param("lastName", "Boyd")
				.contentType("application/json")).andReturn();

		String contentAsString = result.getResponse().getContentAsString();

		// ASSERT
		assertThat(contentAsString).isNotNull();
	}

	@Test
	@DisplayName("getOneMedicalRecord at null")
	void whenValidInputFindOneMRAtNull_thenReturnsNull() throws Exception {
		// ARRANGE
		String firstName = "Jean";
		String lastName = "Bois";
		MedicalRecordDTO mr1 = null;

		when(medicalRecordService.getOneMedicalRecord(firstName, lastName)).thenReturn(mr1);
		// ACT
		MvcResult result = mockMvc.perform(get("/medicalrecord").param("firstName", "Jacob").param("lastName", "Boyd")
				.contentType("application/json")).andReturn();

		String contentAsString = result.getResponse().getContentAsString();

		// ASSERT
		assertThat(contentAsString).isNullOrEmpty();

	}

	@Test
	@DisplayName("updateOneMedicalRecord")
	void whenValidInputUpdateOneMR_thenReturns302() throws Exception {
		// ARRANGE
		MedicalRecordDTO mr1 = new MedicalRecordDTO("John", "Boyd", "03/03/1984",
				new String[] { "aspirine", "doliprane" }, new String[] { "amidon", "gluten" });

		when(medicalRecordService.updateOneMedicalRecord(Mockito.any(MedicalRecordDTO.class))).thenReturn(mr1);
		// ACT
		ObjectMapper objectMapper = new ObjectMapper();
		String mr1IntoString = objectMapper.writeValueAsString(mr1);
		MvcResult result = mockMvc.perform(put("/medicalrecord/update").param("medicalrecordDTO", mr1IntoString)
				.content(mr1IntoString).contentType("application/json")).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		// ASSERT
		assertThat(contentAsString).isNotNull();
	}

	@Test
	@DisplayName("updateOneMedicalRecord at null")
	void whenValidInputUpdateOneMRAtNull_thenReturnsNull() throws Exception {
		// ARRANGE
		MedicalRecordDTO mr2RequestBody = new MedicalRecordDTO("John", "Boyd", "03/03/1984",
				new String[] { "aspirine", "doliprane" }, new String[] { "amidon", "gluten" });
		MedicalRecordDTO mr1 = null;

		when(medicalRecordService.updateOneMedicalRecord(Mockito.any(MedicalRecordDTO.class))).thenReturn(mr1);
		// ACT
		ObjectMapper objectMapper = new ObjectMapper();
		String mr2IntoString = objectMapper.writeValueAsString(mr2RequestBody);
		MvcResult result = mockMvc.perform(put("/medicalrecord/update").param("medicalrecordDTO", mr2IntoString)
				.content(mr2IntoString).contentType("application/json")).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		// ASSERT
		assertThat(contentAsString).isNullOrEmpty();

	}

	@Test
	@DisplayName("createOneMedicalRecord")
	void whenValidInputCreateOneMR_thenReturns302() throws Exception {
		// ARRANGE
		MedicalRecordDTO mr1 = new MedicalRecordDTO("Jean", "Bois", "06/06/1996",
				new String[] { "dafalgan", "efferalgan" }, new String[] { "poil de chat" });

		when(medicalRecordService.addMedicalRecord(Mockito.any(MedicalRecordDTO.class))).thenReturn(mr1);

		// ACT
		ObjectMapper objectMapper = new ObjectMapper();
		String mr1IntoString = objectMapper.writeValueAsString(mr1);
		MvcResult result = mockMvc
				.perform(post("/medicalrecord/create").content(mr1IntoString).contentType("application/json"))
				.andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		// ASSERT
		assertThat(contentAsString).isNotNull();

	}

	@Test
	@DisplayName("createOneMedicalRecord at null")
	void whenValidInputCreateOneMRAtNull_thenReturnsNull() throws Exception {
		// ARRANGE
		MedicalRecordDTO mr1 = new MedicalRecordDTO("Jean", "Bois", "06/06/1996",
				new String[] { "dafalgan", "efferalgan" }, new String[] { "poil de chat" });
		MedicalRecordDTO mr2 = null;

		when(medicalRecordService.addMedicalRecord(Mockito.any(MedicalRecordDTO.class))).thenReturn(mr2);

		// ACT
		ObjectMapper objectMapper = new ObjectMapper();
		String mr1IntoString = objectMapper.writeValueAsString(mr1);
		MvcResult result = mockMvc
				.perform(post("/medicalrecord/create").content(mr1IntoString).contentType("application/json"))
				.andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		// ASSERT
		assertThat(contentAsString.contains("MedicalRecord already created."));

	}

	@Test
	@DisplayName("deleteOneMedicalRecord")
	void whenValidInputDeleteOneMR_thenReturns302() throws Exception {
		// ARRANGE
		String firstName = "Jean";
		String lastName = "Bois";
		MedicalRecordDTO mr1 = new MedicalRecordDTO("Jean", "Bois", "06/06/1996",
				new String[] { "dafalgan", "efferalgan" }, new String[] { "poil de chat" });

		when(medicalRecordService.getOneMedicalRecord(firstName, lastName)).thenReturn(mr1);
		when(medicalRecordService.deleteOneMedicalRecord(Mockito.any(MedicalRecordDTO.class))).thenReturn(Boolean.TRUE);
		// ACT
		MvcResult result = mockMvc.perform(delete("/medicalrecord/delete").param("firstName", "Jacob")
				.param("lastName", "Boyd").contentType("application/json")).andReturn();

		String contentAsString = result.getResponse().getContentAsString();

		// ASSERT
		assertThat(contentAsString).isEqualTo("");
	}

	@Test
	@DisplayName("deleteOneMedicalRecord at null")
	void whenValidInputDeleteOneMRAtNull_thenReturnsFalse() throws Exception {
		// ARRANGE
		String firstName = "Jean";
		String lastName = "Bois";
		MedicalRecordDTO mr1 = new MedicalRecordDTO("Jean", "Bois", "06/06/1996",
				new String[] { "dafalgan", "efferalgan" }, new String[] { "poil de chat" });

		when(medicalRecordService.getOneMedicalRecord(firstName, lastName)).thenReturn(mr1);
		when(medicalRecordService.deleteOneMedicalRecord(Mockito.any(MedicalRecordDTO.class))).thenReturn(false);
		// ACT
		MvcResult result = mockMvc.perform(delete("/medicalrecord/delete").param("firstName", "Jacob")
				.param("lastName", "Boyd").contentType("application/json")).andReturn();

		String contentAsString = result.getResponse().getContentAsString();

		// ASSERT
		assertThat(contentAsString).isEqualTo("");

	}
}
