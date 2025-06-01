package com.example.UserDept.web.exceptions;

import java.time.Instant;

import com.example.UserDept.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
		log.error("API ERROR -", ex);

		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(),ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(DatabaseConflictException.class)
	public ResponseEntity<StandardError> databaseException(DatabaseConflictException ex, HttpServletRequest request) {
		log.error("API ERROR -", ex);

		HttpStatus status = HttpStatus.CONFLICT;
		StandardError err = new StandardError(Instant.now(), status.value(), ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> MethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request, BindingResult result) {
		log.error("API ERROR -", ex);

		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		StandardError err = new StandardError(Instant.now(), status.value(), "Campo inválido(s)", request.getRequestURI(), result);
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<StandardError> InvalidDataException(InvalidDataException ex, HttpServletRequest request) {
		log.error("API ERROR -", ex);

		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		StandardError err = new StandardError(Instant.now(), status.value(), ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(InvalidAuthenticationException.class)
	public ResponseEntity<StandardError> AuthenticationException(Exception ex, HttpServletRequest request) {
		log.error("API ERROR -", ex);

		HttpStatus status = HttpStatus.UNAUTHORIZED;
		StandardError err = new StandardError(Instant.now(), status.value(), ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<StandardError> TokenException(Exception ex, HttpServletRequest request) {
		log.error("API ERROR -", ex);

		HttpStatus status = HttpStatus.UNAUTHORIZED;
		StandardError err = new StandardError(Instant.now(), status.value(), "Error in token generation or validation.", request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<StandardError> AuthorizationDeniedException(AuthorizationDeniedException ex, HttpServletRequest request) {
		log.error("API ERROR -", ex);

		HttpStatus status = HttpStatus.FORBIDDEN;
		StandardError err = new StandardError(Instant.now(), status.value(), "You do not have permission to access this resource.", request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	/**
	 * Fallback handler for unexpected exceptions.
	 * @param ex the unhandled exception
	 * @return a generic 500 Internal Server Error response
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneric(Exception ex) {
		log.error("API ERROR -", ex);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("A server internal error occurs.");
	}
}
