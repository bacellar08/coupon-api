package com.bacellar08.teste_cupom.controller.impl;

import com.bacellar08.teste_cupom.domain.dto.CouponRequest;
import com.bacellar08.teste_cupom.domain.dto.CouponResponse;
import com.bacellar08.teste_cupom.exception.GlobalExceptionHandler;
import com.bacellar08.teste_cupom.service.CouponService;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CouponControllerImpl.class)
@Import(GlobalExceptionHandler.class)
class CouponControllerImplTests {

	@Autowired
	MockMvc mockMvc;
	@MockitoBean
	CouponService couponService;
	@Autowired
	ObjectMapper objectMapper;

	Instant expirationDate;
	CouponRequest couponRequest;
	CouponResponse couponResponse;

	@BeforeEach
	void setup() {
		expirationDate = Instant.now().plus(30, ChronoUnit.DAYS);
	}

	@Test
	void shouldCreateCouponSuccess() {
		givenCouponRequest();
		givenCouponResponse();
		whenServiceCreateCoupon();
		thenAssertCouponCreated();
	}

	@Test
	void shouldFindCouponSuccess() {
		givenCouponResponse();
		whenServiceFindCouponById();
		thenAssertCouponFound();
	}

	@Test
	void shouldDeleteCouponSuccess() {
		whenServiceDeleteCouponById();
		thenAssertCouponDeleted();
	}

	@SneakyThrows
	private void thenAssertCouponCreated() {
		mockMvc.perform(
				post("/v1/coupon")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(couponRequest))
		).andExpect(status().isCreated())
		 .andExpect(jsonPath("$.id").value(couponResponse.id()))
		 .andExpect(jsonPath("$.code").value(couponResponse.code()))
		 .andExpect(jsonPath("$.description").value(couponResponse.description()))
		 .andExpect(jsonPath("$.discountValue").value(couponResponse.discountValue()))
		 .andExpect(jsonPath("$.expirationDate").value(couponResponse.expirationDate().toString()))
		 .andExpect(jsonPath("$.status").value(couponResponse.status()))
		 .andExpect(jsonPath("$.published").value(couponResponse.published()))
		 .andExpect(jsonPath("$.redeemed").value(couponResponse.redeemed()));
	}

	@SneakyThrows
	private void thenAssertCouponFound() {
		mockMvc.perform(
				get("/v1/coupon/{id}",
						"urn:coupon:123e4567-e89b-12d3-a456-426614174000")
		).andExpect(status().isOk())
		 .andExpect(jsonPath("$.id").value(couponResponse.id()))
		 .andExpect(jsonPath("$.code").value(couponResponse.code()))
		 .andExpect(jsonPath("$.description").value(couponResponse.description()))
		 .andExpect(jsonPath("$.discountValue").value(couponResponse.discountValue()))
		 .andExpect(jsonPath("$.expirationDate").value(couponResponse.expirationDate().toString()))
		 .andExpect(jsonPath("$.status").value(couponResponse.status()))
		 .andExpect(jsonPath("$.published").value(couponResponse.published()))
		 .andExpect(jsonPath("$.redeemed").value(couponResponse.redeemed()));
	}

	@SneakyThrows
	private void thenAssertCouponDeleted() {
		mockMvc.perform(
				delete("/v1/coupon/{id}",
						"urn:coupon:123e4567-e89b-12d3-a456-426614174000")
		).andExpect(status().isNoContent());
	}

	private void whenServiceCreateCoupon() {
		when(couponService.createCoupon(couponRequest))
				.thenReturn(couponResponse);
	}

	private void whenServiceFindCouponById() {
		when(couponService.findCoupon("urn:coupon:123e4567-e89b-12d3-a456-426614174000"))
				.thenReturn(couponResponse);
	}

	private void whenServiceDeleteCouponById() {
		doNothing().when(couponService).deleteCoupon("urn:coupon:123e4567-e89b-12d3-a456-426614174000");
	}

	private void givenCouponResponse() {
		couponResponse = new CouponResponse(
				"urn:coupon:123e4567-e89b-12d3-a456-426614174000",
				"ABC123",
				"Teste coupon",
				BigDecimal.valueOf(10.0),
				expirationDate,
				"ACTIVE",
				true,
				false
		);
	}

	private void givenCouponRequest() {
		couponRequest = new CouponRequest(
				"ABC123",
				"Teste coupon",
				BigDecimal.valueOf(10.0),
				expirationDate,
				true
		);
	}
}