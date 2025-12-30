package com.bacellar08.teste_cupom.exception;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class BusinessException extends RuntimeException {

	private final HttpStatusCode httpStatusCode;

	private final List<MessageException> messages = new ArrayList<>();

	public BusinessException() {
		this.httpStatusCode = loadDefaultStatusCode();
	}

	private HttpStatus loadDefaultStatusCode() {
		return HttpStatus.UNPROCESSABLE_CONTENT;
	}

	private HttpStatusCode loadDefaultStatusCode(HttpStatusCode statusCode) {
		return statusCode != null ? statusCode : HttpStatus.UNPROCESSABLE_CONTENT;
	}

	public BusinessException(MessageException message) {
		super(message != null ? message.toString() : null);
		this.httpStatusCode = loadDefaultStatusCode();
		if (message != null) {
			this.messages.add(message);
		}
	}

	public BusinessException(MessageException message, HttpStatusCode statuscode) {
		super(message != null ? message.toString() : null);
		this.httpStatusCode = loadDefaultStatusCode(statuscode);
		if (message != null) {
			this.messages.add(message);
		}
	}

	public BusinessException(String message) {
		super(message);
		this.httpStatusCode = loadDefaultStatusCode();
	}

}
