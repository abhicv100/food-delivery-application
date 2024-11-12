package com.bits.pilani.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bits.pilani.to.ErrorResponseTO;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	ResponseEntity<Object> handleException(Exception exception)  {		
		ErrorResponseTO errorResponse = new ErrorResponseTO();
		errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorResponse.setError(HttpStatus.INTERNAL_SERVER_ERROR.name());			
		errorResponse.setMessage(exception.getMessage());
		return ResponseEntity.internalServerError().body(errorResponse);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatusCode statusCode, WebRequest request) {
		
		ErrorResponseTO errorResponse = new ErrorResponseTO();
		
		if(statusCode instanceof HttpStatus httpStatus) {
			errorResponse.setStatus(httpStatus.value());
			errorResponse.setError(httpStatus.name());			
		}
		errorResponse.setMessage(ex.getMessage());
		return ResponseEntity.status(statusCode).headers(headers).body(errorResponse);
	}
}
