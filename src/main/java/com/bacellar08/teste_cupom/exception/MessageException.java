package com.bacellar08.teste_cupom.exception;

import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class MessageException {

	@NonNull
	protected String code;

	protected String title;

	@NonNull
	protected String detail;

	@NotNull
	private ZonedDateTime requestDateTime;

	public MessageException(HttpStatus code, String title, String detail, ZonedDateTime requestDateTime) {
		this.code = String.valueOf(code.value());
		this.title = title;
		this.detail = detail;
		this.requestDateTime = requestDateTime;
	}
}