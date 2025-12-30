package com.bacellar08.teste_cupom.domain.factory;

import com.bacellar08.teste_cupom.domain.model.Coupon;
import com.bacellar08.teste_cupom.domain.model.CouponCode;
import com.bacellar08.teste_cupom.domain.model.Discount;
import com.bacellar08.teste_cupom.domain.model.StatusEnum;
import com.bacellar08.teste_cupom.exception.BusinessException;
import com.bacellar08.teste_cupom.exception.MessageException;
import java.math.BigDecimal;
import java.time.Instant;

public class CouponFactory {

	public static Coupon create(
			String code,
			String description,
			BigDecimal discountValue,
			Instant expirationDate,
			boolean published
	) {
		var couponCode = new CouponCode(code);
		var discount = new Discount(discountValue);

		if (description == null || description.isBlank()) {
			throw new BusinessException(MessageException.builder()
					.code("CPN-003")
					.title("Invalid coupon")
					.detail("Description must not be blank")
					.build());
		}

		if (expirationDate == null) {
			throw new BusinessException(MessageException.builder()
					.code("CPN-004")
					.title("Invalid coupon")
					.detail("Expiration date must not be null")
					.build());
		}

		if (expirationDate.isBefore(Instant.now())) {
			throw new BusinessException(MessageException.builder()
					.code("CPN-004")
					.title("Invalid coupon")
					.detail("Expiration date must be in the future")
					.build());
		}

		return Coupon.builder()
				.code(couponCode)
				.description(description)
				.discountValue(discount)
				.expirationDate(expirationDate)
				.published(published)
				.status(StatusEnum.ACTIVE)
				.build();
	}
}
