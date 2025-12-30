package com.bacellar08.teste_cupom.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.Instant;

public record CouponRequest(
		String code,
		String description,
		@Schema(description = "Valor do desconto do cupom", example = "15.00")
		BigDecimal discountValue,
		Instant expirationDate,
		boolean published
) {
}
