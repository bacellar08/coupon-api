package com.bacellar08.teste_cupom.domain.model;

import com.bacellar08.teste_cupom.exception.BusinessException;
import com.bacellar08.teste_cupom.exception.MessageException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CouponCodeTests {

	CouponCode code;
	String couponString;
	MessageException exception;

	@Test
	void shouldRemoveSpecialCharactersAndLimitToSixCharacters() {
		givenValidCodeWithSpecialCharacters();
		whenCreateCouponCode();
		thenAssertCodeIsFormattedCorrectly();
	}

	@Test
	void shouldThrowExceptionWhenCodeIsNull() {
		givenEmptyCouponString();
		whenCreateInvalidCouponCode();
		thenAssertExceptionIsThrownForNullCode();
	}

	@Test
	void shouldThrowExceptionWhenCodeIsInvalid() {
		givenInvalidCouponString();
		whenCreateInvalidCouponCode();
		thenAssertExceptionIsThrownForInvalidCode();
	}

	private void thenAssertExceptionIsThrownForNullCode() {
		assertEquals("CPN-001", exception.getCode());
		assertEquals("Invalid coupon code", exception.getTitle());
		assertEquals("Coupon code cannot be null or blank", exception.getDetail());
	}

	private void thenAssertExceptionIsThrownForInvalidCode() {
		assertEquals("CPN-001", exception.getCode());
		assertEquals("Invalid coupon code", exception.getTitle());
		assertEquals("Coupon code must have exactly 6 alphanumeric characters", exception.getDetail());
	}

	private void givenEmptyCouponString() {
		couponString = null;
	}

	private void givenInvalidCouponString() {
		couponString = "ABCD1234";
	}

	private void thenAssertCodeIsFormattedCorrectly() {
		assertNotNull(code);
		assertEquals("ABC123", code.getCodeValue());
	}

	private void whenCreateCouponCode() {
		code = new CouponCode(couponString);
	}

	private void whenCreateInvalidCouponCode() {
		exception = assertThrows(BusinessException.class, () ->
				new CouponCode(couponString)
		).getMessages().getFirst();
	}

	private void givenValidCodeWithSpecialCharacters() {
		couponString = "A@B#C$1%2^3";
	}
}