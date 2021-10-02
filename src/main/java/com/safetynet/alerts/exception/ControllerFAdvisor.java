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
public class ControllerFAdvisor extends ResponseEntityExceptionHandler {

	public static final Logger myLogger = LogManager.getLogger(ControllerFAdvisor.class);

	@ExceptionHandler(FNotFoundException.class)
	public ResponseEntity<Object> handleFirestationNotFoundException(FNotFoundException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "Firestations not found.");

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(FAlreadyCreatedException.class)
	public ResponseEntity<Object> handleFirestationAlreadyCreatedException(FAlreadyCreatedException ex,
			WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "Firestation already created.");

		return new ResponseEntity<>(body, HttpStatus.FOUND);
	}
}