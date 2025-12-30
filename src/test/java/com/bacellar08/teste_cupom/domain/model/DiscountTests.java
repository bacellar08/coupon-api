package com.bacellar08.teste_cupom.domain.model;

import com.bacellar08.teste_cupom.exception.BusinessException;
import com.bacellar08.teste_cupom.exception.MessageException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DiscountTests {
	
	Discount discount;
	BigDecimal value;
	MessageException exception;

	@Test
	void shouldCreateDiscountWithValidValue() {
		givenValidDiscountValue();
		whenCreateValidDiscount();
		thenAssertValidDiscountCreated();
	}

	@Test
	void shouldThrowExceptionWhenDiscountValueIsNull() {
		givenNullDiscountValue();
		whenCreateInvalidDiscount();
		thenAssertExceptionIsThrownForNullValue();
	}

	@Test
	void shouldThrowExceptionWhenDiscountValueIsLessThanMinimum() {
		givenInvalidDiscountValue();
		whenCreateInvalidDiscount();
		thenAssertExceptionIsThrownForInvalidValue();
	}

	private void thenAssertExceptionIsThrownForInvalidValue() {
		assertEquals("CPN-002", exception.getCode());
		assertEquals("Invalid discount value", exception.getTitle());
		assertEquals("Discount value must be at least 0.5", exception.getDetail());
	}

	private void thenAssertExceptionIsThrownForNullValue() {
		assertEquals("CPN-002", exception.getCode());
		assertEquals("Invalid discount value", exception.getTitle());
		assertEquals("Discount value cannot be null", exception.getDetail());
	}

	private void givenNullDiscountValue() {
		value = null;
	}

	private void givenInvalidDiscountValue() {
		value = BigDecimal.valueOf(0.3);
	}

	private void thenAssertValidDiscountCreated() {
		assertNotNull(discount);
		assertEquals(value, discount.getDiscountValue());
	}

	private void givenValidDiscountValue() {
		value = BigDecimal.valueOf(10.0);
	}

	private void whenCreateValidDiscount() {
		discount = new Discount(value);
	}

	private void whenCreateInvalidDiscount() {
		exception = assertThrows(BusinessException.class, () ->
				new Discount(value)
		).getMessages().getFirst();
	}

}