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
class UrlControllerIT {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@BeforeEach
	void SetUpApplicationContext() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@DisplayName("Test Url7")
	void whenValidInputUrl7_thenReturnsListOfString() throws Exception {
		MvcResult result = mockMvc
				.perform(get("/communityEmail").param("city", "Culver").contentType("application/json")).andReturn();

		String contentAsString = result.getResponse().getContentAsString();

		assertThat(contentAsString).isNotNull();
	}

	@Test
	@DisplayName("Test Url6")
	void whenValidInputUrl6_thenReturns302() throws Exception {

		mockMvc.perform(
				get("/personInfo").param("firstName", "John").param("lastName", "Boyd").contentType("application/json"))
				.andExpect(status().isFound());
	}

	@Test
	@DisplayName("Test Url5")
	void whenValidInputUrl5_thenReturns302() throws Exception {

		mockMvc.perform(get("/flood/stations").param("stations", "3, 2, 1").contentType("application/json"))
				.andExpect(status().isFound());
	}

	@Test
	@DisplayName("Test Url4")
	void whenValidInputUrl4_thenReturns302() throws Exception {

		mockMvc.perform(get("/fire").param("address", "1509 Culver St").contentType("application/json"))
				.andExpect(status().isFound());
	}

	@Test
	@DisplayName("Test Url3")
	void whenValidInputUrl3_thenReturns302() throws Exception {

		mockMvc.perform(get("/phoneAlert").param("stationNumber", "3").contentType("application/json"))
				.andExpect(status().isFound());
	}

	@Test
	@DisplayName("Test Url2")
	void whenValidInputUrl2_thenReturns302() throws Exception {

		mockMvc.perform(get("/childAlert").param("address", "892 Downing Ct").contentType("application/json"))
				.andExpect(status().isFound());
	}

	@Test
	@DisplayName("Test Url1")
	void whenValidInputUrl1_thenReturns302() throws Exception {

		mockMvc.perform(get("/firestation").param("stationNumber", "3").contentType("application/json"))
				.andExpect(status().isFound());
	}
}
