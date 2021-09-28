package com.safetynet.alerts.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerMRAdvisor extends ResponseEntityExceptionHandler {

	public static final Logger myLogger = LogManager.getLogger(ControllerMRAdvisor.class);

	@ExceptionHandler(MRNotFoundException.class)
	public ResponseEntity<Object> handleMedicalRecordNotFoundException(MRNotFoundException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "MedicalRecords not found");

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
}
