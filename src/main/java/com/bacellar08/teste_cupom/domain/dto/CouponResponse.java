package com.bacellar08.teste_cupom.domain.dto;

import com.bacellar08.teste_cupom.domain.model.Coupon;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.Instant;

public record CouponResponse(
		String id,
		String code,
		String description,
		@Schema(description = "Valor do desconto do cupom", example = "15.00")
		BigDecimal discountValue,
		Instant expirationDate,
		@Schema(description = "Status do cupom", example = "ACTIVE")
		String status,
		boolean published,
		boolean redeemed
) {
	public static CouponResponse fromEntity(Coupon coupon) {
		return new CouponResponse(
				coupon.getId(),
				coupon.getCode().getCodeValue(),
				coupon.getDescription(),
				coupon.getDiscountValue().getDiscountValue(),
				coupon.getExpirationDate(),
				coupon.getStatus().name(),
				coupon.isPublished(),
				coupon.isRedeemed()
		);
	}
}
