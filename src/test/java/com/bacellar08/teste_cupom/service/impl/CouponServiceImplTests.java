package com.bacellar08.teste_cupom.service.impl;

import com.bacellar08.teste_cupom.domain.dto.CouponRequest;
import com.bacellar08.teste_cupom.domain.dto.CouponResponse;
import com.bacellar08.teste_cupom.domain.factory.CouponFactory;
import com.bacellar08.teste_cupom.domain.model.Coupon;
import com.bacellar08.teste_cupom.domain.model.StatusEnum;
import com.bacellar08.teste_cupom.exception.BusinessException;
import com.bacellar08.teste_cupom.exception.MessageException;
import com.bacellar08.teste_cupom.repository.CouponRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CouponServiceImplTests {

	@Mock
	CouponRepository repository;

	@InjectMocks
	CouponServiceImpl service;

	CouponRequest couponRequest;
	CouponResponse couponResponse;
	Instant expirationDate;
	MessageException exception;

	@BeforeEach
	void setup() {
		expirationDate = Instant.now().plus(30, ChronoUnit.DAYS);
	}

	@Test
	void shouldCreateCouponSuccess() {
		givenValidCouponRequest();
		givenSavedCoupon();
		whenCreateCoupon();
		thenAssertCouponCreated();
	}

	@Test
	void shouldFindCouponSuccess() {
		givenCouponFoundInRepository();
		whenFindCouponById();
		thenAssertCouponFound();
	}

	@Test
	void shouldThrowNotFoundWhenFindById() {
		givenCouponNotFound();
		whenFindCouponById();
		thenAssertCouponNotFound();
	}

	@Test
	void shouldDeleteCouponSuccess() {
		givenCouponFoundInRepository();
		whenDeleteCouponById();
		thenAssertCouponDeleted();
	}

	@Test
	void shouldThrowNotFoundWhenDeleteById() {
		givenCouponNotFound();
		whenDeleteCouponById();
		thenAssertCouponNotFound();
	}

	private void givenCouponFoundInRepository() {
		when(repository.findById(anyString())).thenReturn(
				Optional.of(
						CouponFactory.create(
								"ABC123",
								"Test Coupon",
								BigDecimal.valueOf(10.0),
								expirationDate,
								true
						)
				)
		);
	}

	private void givenCouponNotFound() {
		when(repository.findById(anyString())).thenReturn(Optional.ofNullable(null));
	}

	private void givenSavedCoupon() {
		when(repository.save(any(Coupon.class))).thenReturn(
						CouponFactory.create(
								"ABC123",
								"Test Coupon",
								BigDecimal.valueOf(10.0),
								expirationDate,
								true
						)
		);
	}

	private void thenAssertCouponCreated() {
		verify(repository).save(any(Coupon.class));
		assertNotNull(couponResponse);
		assertEquals(couponRequest.code(), couponResponse.code());
		assertEquals(couponRequest.description(), couponResponse.description());
		assertEquals(couponRequest.discountValue(), couponResponse.discountValue());
		assertEquals(couponRequest.expirationDate(), couponResponse.expirationDate());
		assertEquals(StatusEnum.ACTIVE.name(), couponResponse.status());
		assertEquals(couponRequest.published(), couponResponse.published());
		assertFalse(couponResponse.redeemed());
	}

	private void thenAssertCouponFound() {
		assertNotNull(couponResponse);
		assertEquals("ABC123", couponResponse.code());
		assertEquals("Test Coupon", couponResponse.description());
		assertEquals(BigDecimal.valueOf(10.0), couponResponse.discountValue());
		assertEquals(expirationDate, couponResponse.expirationDate());
		assertEquals(StatusEnum.ACTIVE.name(), couponResponse.status());
		assertTrue(couponResponse.published());
		assertFalse(couponResponse.redeemed());
	}

	private void thenAssertCouponDeleted() {
		verify(repository).save(any(Coupon.class));
	}

	private void thenAssertCouponNotFound() {
		assertNotNull(exception);
		assertEquals("CPN-006", exception.getCode());
		assertEquals("Coupon not found", exception.getTitle());
		assertEquals("Coupon with id couponId not found", exception.getDetail());
	}

	private void whenCreateCoupon() {
		couponResponse = service.createCoupon(couponRequest);
	}

	private void whenFindCouponById() {
		try {
			couponResponse = service.findCoupon("couponId");
		} catch (BusinessException ex) {
			exception = ex.getMessages().getFirst();
		}
	}

	private void whenDeleteCouponById() {
		try {
			service.deleteCoupon("couponId");
		} catch (BusinessException ex) {
			exception = ex.getMessages().getFirst();
		}
	}

	private void givenValidCouponRequest() {
		couponRequest = new CouponRequest(
				"ABC123",
				"Test Coupon",
				BigDecimal.valueOf(10.0),
				expirationDate,
				true
		);
	}
}