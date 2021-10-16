package com.safetynet.alerts.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CalculateAge {
	public static final Logger logger = LogManager.getLogger(CalculateAge.class);

	public CalculateAge() {
		// constructor
	}

	public int calculateAgeOfPerson(String customBirthDate) {
		int age = 0;

		// define a customized format
		DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		// transform to iso format
		String isoBd = transformFomCustomToIsoFormat(customBirthDate);
		if (isoBd != null) {
			// transform from string to localDate at iso format
			LocalDate birthdate = LocalDate.parse(isoBd);

			LocalDate today = LocalDate.now();
			// apply this custom format
			customFormatter.format(today);

			// calculate the age
			Period period = Period.between(birthdate, today);
			age = period.getYears();
		}
		return age;
	}

	// transform from customFormat "MM/dd/yyyy" to isoFormat "yyyy-MM-dd"
	public String transformFomCustomToIsoFormat(String customFormat) {
		String isoFormatString = null;
		String year;
		String month;
		String day;
		String[] intermediate;

		try {
			if (customFormat == null) {
				return null;
			} else {
				if (customFormat.contains("/")) {
					intermediate = customFormat.split("/");

					month = intermediate[0];
					if ((Integer.parseInt(month) >= 1) && (Integer.parseInt(month) <= 12)) {
						// we are going on
					} else {
						return null;
					}

					day = intermediate[1];
					if ((Integer.valueOf(day) >= 1) && (Integer.valueOf(day) <= 31)) {
						// we are going on
					} else {
						return null;
					}
					year = intermediate[2];

					isoFormatString = year + "-" + month + "-" + day;
				}
			}
		} catch (Exception ex) {
			logger.error("Error : %s %n", ex.getMessage());
		}
		return isoFormatString;
	}
}
