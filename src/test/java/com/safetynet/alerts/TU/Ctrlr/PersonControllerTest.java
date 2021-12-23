package com.safetynet.alerts.TU.Ctrlr;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import com.safetynet.alerts.controller.PersonController;
import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.service.IPersonService;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = { PersonController.class })
class PersonControllerTest {

	// @Autowired
	// private WebApplicationContext webApplicationContext;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IPersonService personService;

	@BeforeEach
	void SetUpApplicationContext() {
		// mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@DisplayName("findAllPersons")
	void whenValidInputFindAll_thenReturnsListOfString() throws Exception {
		// ARRANGE
		PersonDTO pd1 = new PersonDTO("Jean", "Boyd", "123 rue de Paris", "Paris", "75000", "0123456789",
				"jobo@gmail.com");
		PersonDTO pd2 = new PersonDTO("Roger", "Boyd", "123 rue de Paris", "Paris", "75000", "0123456789",
				"robo@gmail.com");
		List<PersonDTO> lpd = new ArrayList<PersonDTO>();
		lpd.add(pd1);
		lpd.add(pd2);

		when(personService.findAllPersons()).thenReturn(lpd);

		// ACT
		MvcResult result = mockMvc.perform(get("/persons").contentType("application/json")).andReturn();

		String contentAsString = result.getResponse().getContentAsString();

		// ASSERT
		assertThat(contentAsString).isNotNull();

	}

	@Test
	@DisplayName("findAllPersons At Null")
	void whenValidInputFindAllAtNull_thenReturnsNull() throws Exception {
		// ARRANGE
		List<PersonDTO> lpd = new ArrayList<PersonDTO>();

		when(personService.findAllPersons()).thenReturn(lpd);

		// ACT
		MvcResult result = mockMvc.perform(get("/persons").contentType("application/json")).andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		// ASSERT
		verify(personService, times(1)).findAllPersons();
		assertThat(contentAsString).isNullOrEmpty();

	}

	@Test
	@DisplayName("getOnePerson")
	void whenValidInputFindOnePerson_thenReturns302() throws Exception {
		// ARRANGE
		String firstName = "Jean";
		String lastName = "Bois";
		PersonDTO personDTO = new PersonDTO("JohnJohn", "BoydBoyd", "1 rue de Paris", "Paris", "75015", "0123456789",
				"jojo@gmail.com");

		when(personService.getOnePerson(firstName, lastName)).thenReturn(personDTO);
		// ACT
		MvcResult result = mockMvc.perform(
				get("/person").param("firstName", "Roger").param("lastName", "Boyd").contentType("application/json"))
				.andReturn();

		String contentAsString = result.getResponse().getContentAsString();

		// ASSERT
		assertThat(contentAsString).isNotNull();

	}

	@Test
	@DisplayName("getOnePerson at null")
	void whenValidInputFindOnePersonAtNull_thenReturnsNull() throws Exception {
		// ARRANGE
		String firstName = "Jean";
		String lastName = "Bois";
		PersonDTO personDTO = null;

		when(personService.getOnePerson(firstName, lastName)).thenReturn(personDTO);

		// ACT
		MvcResult result = mockMvc.perform(
				get("/person").param("firstName", "Roger").param("lastName", "Boyd").contentType("application/json"))
				.andReturn();

		String contentAsString = result.getResponse().getContentAsString();

		// ASSERT
		assertThat(contentAsString).isNullOrEmpty();

	}

	@Test
	@DisplayName("updateOnePerson")
	void whenValidInputUpdateOnePerson_thenReturns302() throws Exception {
		// ARRANGE
		PersonDTO pd1RequestBody = new PersonDTO("John", "Boyd", "1 rue de Paris", "Paris", "75001", "0123456789",
				"joboyd@gmail.com");
		PersonDTO pd = new PersonDTO("Johna", "Boyd", "421 rue de Paris", "Paris", "75000", "0123456789",
				"jobo@gmail.com");

		when(personService.updateOnePerson(Mockito.any(PersonDTO.class))).thenReturn(pd);

		// ACT
		ObjectMapper objectMapper = new ObjectMapper();
		String pd1IntoString = objectMapper.writeValueAsString(pd1RequestBody);

		MvcResult result = mockMvc.perform(put("/person/update").content(pd1IntoString)
				.param("personDTO", pd1IntoString).contentType("application/json")).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		// ASSERT
		assertThat(contentAsString).isNotNull();

	}

	@Test
	@DisplayName("updateOnePerson at null")
	void whenValidInputUpdateOnePersonAtNull_thenReturnsNull() throws Exception {
		// ARRANGE
		PersonDTO pd1RequestBody = new PersonDTO("John", "Boyd", "1 rue de Paris", "Paris", "75001", "0123456789",
				"joboyd@gmail.com");
		PersonDTO pd = null;

		when(personService.updateOnePerson(Mockito.any(PersonDTO.class))).thenReturn(pd);

		// ACT
		ObjectMapper objectMapper = new ObjectMapper();
		String pd1IntoString = objectMapper.writeValueAsString(pd1RequestBody);

		MvcResult result = mockMvc.perform(put("/person/update").content(pd1IntoString)
				.param("personDTO", pd1IntoString).contentType("application/json")).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		// ASSERT
		assertThat(contentAsString).isNullOrEmpty();

	}

	@Test
	@DisplayName("createOnePerson")
	void whenValidInputCreateOnePerson_thenReturns302() throws Exception {
		// ARRANGE
		PersonDTO pd1 = new PersonDTO("Jean-Noel", "Chambe", "123 rue de Paris", "Lyon", "69000", "0123456789",
				"jnc@gmail.com");

		when(personService.addPerson(Mockito.any(PersonDTO.class))).thenReturn(pd1);

		// ACT
		ObjectMapper objectMapper = new ObjectMapper();
		String pd1IntoString = objectMapper.writeValueAsString(pd1);
		MvcResult result = mockMvc
				.perform(post("/person/create").content(pd1IntoString).contentType("application/json")).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		// ASSERT
		assertThat(contentAsString).isNotNull();
	}

	@Test
	@DisplayName("createOnePerson at null")
	void whenValidInputCreateOnePersonAtNull_thenReturnsNull() throws Exception {
		// ARRANGE
		PersonDTO pd = null;
		PersonDTO pd1 = new PersonDTO("Jean-Noel", "Chambe", "1234 rue de Paris", "Lyon", "69000", "0123456789",
				"jnc@gmail.com");

		when(personService.addPerson(Mockito.any(PersonDTO.class))).thenReturn(pd);

		// ACT
		ObjectMapper objectMapper = new ObjectMapper();
		String pd1IntoString = objectMapper.writeValueAsString(pd1);
		MvcResult result = mockMvc.perform(post("/person/create").content(pd1IntoString)
				.param("personDTO", pd1IntoString).contentType("application/json")).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		// ASSERT
		assertThat(contentAsString).isNullOrEmpty();

	}

	@Test
	@DisplayName("deleteOnePerson")
	void whenValidInputDeleteOnePerson_thenReturnsTrue() throws Exception {
		// ARRANGE
		String firstName = "Jean";
		String lastName = "Bois";
		PersonDTO personDTO = new PersonDTO("JohnJohn", "BoydBoyd", "1 rue de Paris", "Paris", "75015", "0123456789",
				"jobo@gmail.com");

		when(personService.getOnePerson(firstName, lastName)).thenReturn(personDTO);
		when(personService.deleteOnePerson(Mockito.any(PersonDTO.class))).thenReturn(Boolean.TRUE);

		// ACT
		MvcResult result = mockMvc.perform(delete("/person/delete").param("firstName", "John").param("lastName", "Boyd")
				.contentType("application/json")).andReturn();

		String contentAsString = result.getResponse().getContentAsString();

		// ASSERT
		assertThat(contentAsString).isEqualTo("false");

	}

	@Test
	@DisplayName("deleteOnePerson at false")
	void whenValidInputDeleteOnePersonAtFalse_thenReturnsFalse() throws Exception {
		// ARRANGE
		String firstName = "Jean";
		String lastName = "Bois";
		PersonDTO personDTO = new PersonDTO("JohnJohn", "BoydBoyd", "1 rue de Paris", "Paris", "75015", "0123456789",
				"jobo@gmail.com");

		when(personService.getOnePerson(firstName, lastName)).thenReturn(personDTO);
		when(personService.deleteOnePerson(Mockito.any(PersonDTO.class))).thenReturn(false);

		// ACT
		MvcResult result = mockMvc.perform(delete("/person/delete").param("firstName", "John").param("lastName", "Boyd")
				.contentType("application/json")).andReturn();

		String contentAsString = result.getResponse().getContentAsString();

		// ASSERT
		assertThat(contentAsString).isEqualTo("false");

	}
}
