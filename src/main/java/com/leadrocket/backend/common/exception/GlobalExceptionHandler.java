package com.leadrocket.backend.common.exception;

import com.leadrocket.backend.common.response.RequestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(BadRequestException.class)
	public RequestResponse handleBadRequest(BadRequestException ex) {
		return RequestResponse.error(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler(NotFoundException.class)
	public RequestResponse handleNotFound(NotFoundException ex) {
		return RequestResponse.error(ex.getMessage(), HttpStatus.NOT_FOUND.value());
	}

	@ExceptionHandler(UnauthorizedException.class)
	public RequestResponse handleUnauthorized(UnauthorizedException ex) {
		return RequestResponse.error(ex.getMessage(), HttpStatus.UNAUTHORIZED.value());
	}

	@ExceptionHandler(Exception.class)
	public RequestResponse handleGeneric(Exception ex) {
		return RequestResponse.error(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex) {
		String message = ex.getBindingResult().getFieldErrors()
			.stream().map(f -> f.getField() + ": " + f.getDefaultMessage())
			.reduce((a, b) -> a + "; " + b).orElse("validation failed");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}

	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<String> handleConflict(ConflictException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
}
