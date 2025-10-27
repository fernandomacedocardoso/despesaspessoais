package br.com.desafio.despesaspessoais.exeception;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception,
			WebRequest request) {
		var details = exception.getBindingResult().getFieldErrors().stream()
				.map(error -> new ErrorDetail(error.getField(), error.getDefaultMessage()))
				.collect(Collectors.toList());

		ErrorResponse errorResponse = new ErrorResponse(Instant.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request",
				"Dados inválidos", request.getDescription(false).replace("uri=", ""), details);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<ErrorResponse> handleConflictException(ConflictException exception, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(Instant.now(), HttpStatus.CONFLICT.value(), "Conflict",
				exception.getMessage(), request.getDescription(false).replace("uri=", ""), null);
		return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception,
			WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(Instant.now(), HttpStatus.NOT_FOUND.value(), "Not Found",
				exception.getMessage(), request.getDescription(false).replace("uri=", ""), null);
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
}
// Classes de suporte para o payload de erro
	record ErrorResponse(Instant timestamp, Integer status, String error, String message, String path,
		java.util.List<ErrorDetail> details) {
}

	record ErrorDetail(String field, String message) {

}
