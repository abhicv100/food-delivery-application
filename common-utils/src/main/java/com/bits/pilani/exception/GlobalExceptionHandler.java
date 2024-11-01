package com.bits.pilani.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	ResponseEntity<Object> handleException(Exception exception)  {
		System.out.println(exception.getCause());
		return ResponseEntity.internalServerError().body(exception.getMessage());
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatusCode statusCode, WebRequest request) {
		
		Map<String, Object> errorResponse = new HashMap<>();
		if(statusCode instanceof HttpStatus httpStatus) {
			errorResponse.put("status", httpStatus.value());
			errorResponse.put("error", httpStatus.name());			
		}
		errorResponse.put("message", ex.getMessage());

		return ResponseEntity.status(statusCode).headers(headers).body(errorResponse);
	}
}
