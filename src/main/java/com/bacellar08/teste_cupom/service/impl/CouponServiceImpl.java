package com.bacellar08.teste_cupom.service.impl;

import com.bacellar08.teste_cupom.domain.dto.CouponRequest;
import com.bacellar08.teste_cupom.domain.dto.CouponResponse;
import com.bacellar08.teste_cupom.domain.factory.CouponFactory;
import com.bacellar08.teste_cupom.domain.model.Coupon;
import com.bacellar08.teste_cupom.exception.BusinessException;
import com.bacellar08.teste_cupom.exception.MessageException;
import com.bacellar08.teste_cupom.repository.CouponRepository;
import com.bacellar08.teste_cupom.service.CouponService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

	@NonNull
	private CouponRepository repository;

	@Override
	public CouponResponse createCoupon(CouponRequest request) {
		var coupon = CouponFactory.create(
				request.code(),
				request.description(),
				request.discountValue(),
				request.expirationDate(),
				request.published());

		log.info("[INFO] Saving new coupon with code: {}", request.code());
		var savedCoupon = repository.save(coupon);
		return CouponResponse.fromEntity(savedCoupon);
	}

	@Override
	public CouponResponse findCoupon(String id) {
		var coupon = getCoupon(id);

		log.info("[INFO] Found coupon with id {}", id);
		return CouponResponse.fromEntity(coupon);
	}

	@Override
	public void deleteCoupon(String id) {
		var coupon = getCoupon(id);
		coupon.delete();
		repository.save(coupon);
	}

	private Coupon getCoupon(String id) {
		return repository.findById(id).orElseThrow(() ->
				couponNotFoundException(id)
		);
	}

	private BusinessException couponNotFoundException(String id) {
		log.error("[ERROR] Coupon with id {} not found", id);
		return new BusinessException(MessageException.builder()
				.code("CPN-006")
				.title("Coupon not found")
				.detail("Coupon with id " + id + " not found")
				.build(), HttpStatus.NOT_FOUND);
	}
}
