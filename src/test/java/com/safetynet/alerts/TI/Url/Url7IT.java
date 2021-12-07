package com.safetynet.alerts.TI.Url;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.service.UrlService;

@ExtendWith(MockitoExtension.class)
class Url7IT {
	// To be tested
	@InjectMocks
	private UrlService us = new UrlService();

	@Mock
	private PersonService personService;

	@BeforeAll
	private static void setUp() {

	}

	@BeforeEach
	private void setUpPerTest() {
		List<String> listOfAllMails = new ArrayList<>();
		listOfAllMails.add("jo@gmail.com");
		listOfAllMails.add("ro@gmail.com");
		listOfAllMails.add("tenley@gmail.com");
		listOfAllMails.add("allison@gmail.com");
		when(personService.getAllEmailsFrom(Mockito.any())).thenReturn(listOfAllMails);
	}

	// URL7
	/**
	 * This method checks getting emails of all Persons from a city
	 */
	@ParameterizedTest
	@MethodSource("CitySource")
	@DisplayName("Get emails of persons from the same city")
	void GetEmailsOfPersonsFromCityTest_ShouldReturnListOfEmails(String city) {
		// arrange
		List<String> ls = new ArrayList<>();

		// act
		ls = us.getAllEmailsFrom(city);
		// assert
		assertNotNull(ls, "list of Emails not null");
	}

	// with its data
	private static Stream<Arguments> CitySource() {
		String city1 = "Culver";
		return Stream.of(Arguments.of(city1));
	}

}
