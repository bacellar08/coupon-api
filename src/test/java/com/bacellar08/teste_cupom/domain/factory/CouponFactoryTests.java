package com.bacellar08.teste_cupom.domain.factory;

import com.bacellar08.teste_cupom.domain.model.Coupon;
import com.bacellar08.teste_cupom.domain.model.StatusEnum;
import com.bacellar08.teste_cupom.exception.BusinessException;
import com.bacellar08.teste_cupom.exception.MessageException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CouponFactoryTests {

	Instant expirationDate;
	String description;
	Coupon coupon;
	MessageException exception;

	@Test
	void shouldCreateCouponWithValidDate() {
		givenValidExpirationDate();
		givenDescription();
		whenCreateValidCoupon();
		thenAssertValidCouponCreated();
	}

	@Test
	void shouldThrowExceptionWhenExpirationDateIsInThePast() {
		givenExpirationDateInThePast();
		givenDescription();
		whenCreateCoupon();
		thenAssertExceptionIsThrownWhenInvalidExpirationDate();
	}

	@Test
	void shouldThrowExceptionWhenDescriptionIsNull() {
		givenValidExpirationDate();
		whenCreateCoupon();
		thenAssertExceptionIsThrownWhenDescriptionNull();
	}

	private void thenAssertExceptionIsThrownWhenDescriptionNull() {
		assertEquals("CPN-003", exception.getCode());
		assertEquals("Invalid coupon", exception.getTitle());
		assertEquals("Description must not be blank", exception.getDetail());
	}

	private void thenAssertValidCouponCreated() {
		assertNotNull(coupon);
		assertEquals("ABC123", coupon.getCode().getCodeValue());
		assertEquals(StatusEnum.ACTIVE, coupon.getStatus());
		assertEquals(expirationDate, coupon.getExpirationDate());
		assertTrue(coupon.isPublished());
		assertFalse(coupon.isRedeemed());
	}

	private void whenCreateCoupon() {
		exception = assertThrows(BusinessException.class, () -> CouponFactory.create(
						"ABC123",
						description,
						BigDecimal.valueOf(10),
						expirationDate,
						true
				)
		).getMessages().getFirst();
	}

	private void thenAssertExceptionIsThrownWhenInvalidExpirationDate() {
		assertEquals("CPN-004", exception.getCode());
		assertEquals("Invalid coupon", exception.getTitle());
		assertEquals("Expiration date must be in the future", exception.getDetail());
	}

	private void whenCreateValidCoupon() {
		coupon = CouponFactory.create(
				"ABC-123",
				description,
				BigDecimal.valueOf(10),
				expirationDate,
				true
		);
	}

	private void givenDescription() {
		description = "Black Friday";
	}

	private void givenValidExpirationDate() {
		expirationDate = Instant.now().plus(1, ChronoUnit.DAYS);
	}

	private void givenExpirationDateInThePast() {
		expirationDate = Instant.now().minus(1, ChronoUnit.DAYS);
	}


}