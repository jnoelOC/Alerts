package com.safetynet.alerts.TI.Ctrlr;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MedicalRecordControllerIT {
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@BeforeEach
	void SetUpApplicationContext() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@DisplayName("findAllMedRec")
	void whenValidInputFindAllMR_thenReturnsListOfString() throws Exception {
		MvcResult result = mockMvc.perform(get("/medicalrecords").contentType("application/json")).andReturn();

		String contentAsString = result.getResponse().getContentAsString();

		assertThat(contentAsString).isNotNull();
	}

	@Test
	@DisplayName("getOneMedRec")
	void whenValidInputFindOneMR_thenReturns302() throws Exception {
		mockMvc.perform(get("/medicalrecord").param("firstName", "Roger").param("lastName", "Boyd")
				.contentType("application/json")).andExpect(status().isFound());
	}

}