// Centralized exception handling

package com.leadrocket.backend.common.exception;

import com.leadrocket.backend.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse<Void>> handle(ApiException ex) {
		return ResponseEntity
				.status(ex.getStatus())
				.body(ApiResponse.error(ex.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleUnknown(Exception ex) {
		return ResponseEntity
				.status(500)
				.body(ApiResponse.error("Internal server error"));
	}
}
