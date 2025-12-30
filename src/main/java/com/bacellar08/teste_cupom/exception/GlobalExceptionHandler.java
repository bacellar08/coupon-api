package com.bacellar08.teste_cupom.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<MessageException> handleBusinessException(BusinessException ex) {

		log.error("Business rule violated: {}", ex.getMessage());

		MessageException message = ex.getMessages().getFirst();
		message.setRequestDateTime(ZonedDateTime.now(ZoneOffset.UTC));

		return ResponseEntity
				.status(ex.getHttpStatusCode())
				.body(message);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<MessageException> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {

		Throwable cause = ex;
		log.error("HTTP message not readable: {}", ex.getMessage());
		while (cause != null) {

			if (cause instanceof InvalidFormatException  ||
			cause instanceof DateTimeParseException) {

				log.warn("Invalid date format received: {}", cause.getMessage());

				var message = MessageException.builder()
						.code("CPN-008")
						.title("Invalid Date Format")
						.detail("The provided date format is invalid. Expected: 'yyyy-MM-dd'T'HH:mm:ssXXZ'.")
						.requestDateTime(ZonedDateTime.now(ZoneOffset.UTC))
						.build();

				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body(message);
			}

			cause = cause.getCause();
		}

		var message = MessageException.builder()
				.code("CPN-009")
				.title("Malformed JSON request")
				.detail("Request body is invalid or malformed")
				.requestDateTime(ZonedDateTime.now(ZoneOffset.UTC))
				.build();

		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(message);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<MessageException> handleGenericException(Exception ex) {

		log.error("Unexpected error: {}", ex.getMessage());

		var message = MessageException.builder()
				.code("CPN-000")
				.title("Failed to save coupon")
				.detail("An unexpected error occurred while saving the coupon.")
				.requestDateTime(ZonedDateTime.now(ZoneOffset.UTC))
				.build();

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(message);
	}
}
