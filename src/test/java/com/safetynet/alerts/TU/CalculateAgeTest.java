package com.safetynet.alerts.TU;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.safetynet.alerts.utils.CalculateAge;

class CalculateAgeTest {

	// To be tested
	private CalculateAge ca;

	@BeforeEach
	private void setUpPerTest() {
		ca = new CalculateAge();
	}

	/**
	 * This method checks computing of age for different values
	 */
	@ParameterizedTest
	@MethodSource("AgeSource")
	@DisplayName("Calculate age of person")
	void CalculateAgeTest_ShouldReturnAge(String birthdate) {
		// arrange

		// act
		int age = ca.calculateAgeOfPerson(birthdate);
		// assert
		assertThat(age).isPositive();
	}

	// with its data
	private static Stream<Arguments> AgeSource() {

		return Stream.of(Arguments.of("12/01/2001"), Arguments.of("01/01/2020"), Arguments.of("06/21/2007"),
				Arguments.of("12/31/1901"));
	}

	/**
	 * This method checks computing of age Zero with different values
	 */
	@ParameterizedTest
	@MethodSource("Age0Source")
	@DisplayName("Calculate age zero")
	void CalculateAge0Test_ShouldReturnAge0(String birthdate) {
		// arrange

		// act
		int age = ca.calculateAgeOfPerson(birthdate);
		// assert
		assertThat(age).isZero();
	}

	// with its data
	private static Stream<Arguments> Age0Source() {

		return Stream.of(Arguments.of("21/02/1981"), Arguments.of("00/00/1980"), Arguments.of("2007-12-03"),
				Arguments.of("32/56/2001"), Arguments.of(String.valueOf(LocalDate.now())), Arguments.of("nullTest"),
				null);
	}

	/**
	 * This method checks transforming from custom format to iso format with
	 * different values
	 */
	@ParameterizedTest
	@MethodSource("formatSource")
	@DisplayName("transform custom to iso format")
	void TransformCustomToIsoTest_ShouldReturnIsoFormat(String customFormat) {
		// arrange

		// act
		String isoFormat = ca.transformFomCustomToIsoFormat(customFormat);
		// assert
		assertThat(isoFormat).contains("-");
	}

	// with its data
	private static Stream<Arguments> formatSource() {

		return Stream.of(Arguments.of("12/02/1901"), Arguments.of("07/11/2020"), Arguments.of("12/01/2001"),
				Arguments.of("06/21/2007"), Arguments.of("11/19/1901"));
	}

	/**
	 * This method checks transforming from custom format to null with different
	 * values
	 */
	@ParameterizedTest
	@MethodSource("formatNullSource")
	@DisplayName("transform custom to null format")
	void TransformCustomToIsoTest_ShouldReturnNull(String customFormat) {
		// arrange

		// act
		String isoFormat = ca.transformFomCustomToIsoFormat(customFormat);
		// assert
		assertThat(isoFormat).isNull();
		;
	}

	// with its data
	private static Stream<Arguments> formatNullSource() {

		return Stream.of(Arguments.of("21/02/1981"), Arguments.of("02/89/3000"), Arguments.of("2007-12-03"), null,
				Arguments.of("testnull"));
	}
}
