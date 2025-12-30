package com.bacellar08.teste_cupom.domain.dto;

import com.bacellar08.teste_cupom.domain.Coupon;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record CouponResponse(
		String id,
		String code,
		String description,
		BigDecimal discountValue,
		Instant expirationDate,
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
