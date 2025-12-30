package com.bacellar08.teste_cupom.domain.model;

import com.bacellar08.teste_cupom.domain.factory.CouponFactory;
import com.bacellar08.teste_cupom.exception.BusinessException;
import com.bacellar08.teste_cupom.exception.MessageException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CouponTests {

	private static final String COUPON_ID_REGEX = "urn:coupon:[a-zA-Z0-9\\-]{36}";
	Coupon coupon;
	MessageException exception;

	@Test
	void shouldDeleteCouponSuccessfully() {
		givenActiveCoupon();
		whenDeleteCoupon();
		thenAssertCouponIsDeleted();
	}

	@Test
	void shouldThrownExceptionWhenAlreadyDeleted() {
		givenDeletedCoupon();
		whenDeleteCoupon();
		thenAssertExceptionIsThrown();
	}

	@Test
	void shouldGenerateNewId() {
		givenActiveCoupon();
		whenGenerateId();
		thenAssertNewIdIsGenerated();
	}

	private void thenAssertNewIdIsGenerated() {
		assertNotNull(coupon.getId());
		assertTrue(coupon.getId().matches(COUPON_ID_REGEX));
	}

	private void whenGenerateId() {
		coupon.generateId();
	}

	private void givenDeletedCoupon() {
		coupon = CouponFactory.create(
				"ABC123",
				"Teste",
				BigDecimal.valueOf(10.0),
				Instant.now().plus(10, ChronoUnit.DAYS),
				true
		);
		coupon.delete();
	}

	private void thenAssertCouponIsDeleted() {
		assertEquals(StatusEnum.DELETED, coupon.getStatus());
	}

	private void thenAssertExceptionIsThrown() {
		assertEquals("CPN-007", exception.getCode());
		assertEquals("Invalid operation", exception.getTitle());
		assertEquals("Coupon is already deleted", exception.getDetail());
	}

	private void whenDeleteCoupon() {
		try {
			coupon.delete();
		} catch (BusinessException e) {
			exception = e.getMessages().getFirst();
		}
	}

	private void givenActiveCoupon() {
		coupon = CouponFactory.create(
				"ABC123",
				"Teste",
				BigDecimal.valueOf(10.0),
				Instant.now().plus(10, ChronoUnit.DAYS),
				true
		);

	}
}