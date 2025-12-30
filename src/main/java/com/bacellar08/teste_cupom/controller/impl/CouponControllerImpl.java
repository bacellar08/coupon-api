package com.bacellar08.teste_cupom.controller.impl;

import com.bacellar08.teste_cupom.controller.CouponController;
import com.bacellar08.teste_cupom.domain.dto.CouponRequest;
import com.bacellar08.teste_cupom.domain.dto.CouponResponse;
import com.bacellar08.teste_cupom.service.CouponService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CouponControllerImpl implements CouponController {

	@NonNull
	private final CouponService service;

	@Override
	public ResponseEntity<CouponResponse> createCoupon(CouponRequest coupon) {
		log.info("[START] Request to create coupon: {}", coupon);
		var createdCoupon = service.createCoupon(coupon);

		log.info("[END] Coupon created: {}", createdCoupon);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCoupon);
	}

	@Override
	public ResponseEntity<CouponResponse> findCoupon(String id) {
		log.info("[START] Request to find coupon by id: {}", id);
		var foundCoupon = service.findCoupon(id);

		log.info("[END] Coupon found: {}", foundCoupon);
		return ResponseEntity.ok(foundCoupon);
	}

	@Override
	public ResponseEntity<Void> deleteCoupon(String id) {
		log.info("[START] Request to delete coupon by id: {}", id);
		service.deleteCoupon(id);

		log.info("[END] Coupon with id {} deleted", id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
