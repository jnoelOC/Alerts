package com.safetynet.alerts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Alerts1Application {

	public static final Logger logger = LogManager.getLogger("Alerts1Application");

	public static void main(String[] args) {
		logger.info("Initializing SafetyNet");
		SpringApplication.run(Alerts1Application.class, args);

	}

}
