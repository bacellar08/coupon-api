package com.bacellar08.teste_cupom.domain.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record CouponRequest(
		String code,
		String description,
		BigDecimal discountValue,
		Instant expirationDate,
		boolean published
) {
}
