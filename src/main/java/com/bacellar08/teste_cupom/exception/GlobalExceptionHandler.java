package com.bacellar08.teste_cupom.exception;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<MessageException> handleBusinessException(BusinessException ex) {

		log.warn("Business rule violated: {}", ex.getMessage());

		MessageException message = ex.getMessages().getFirst();
		message.setRequestDateTime(ZonedDateTime.now(ZoneOffset.UTC));

		return ResponseEntity
				.status(ex.getHttpStatusCode())
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
