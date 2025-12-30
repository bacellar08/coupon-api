package com.bacellar08.teste_cupom.service;

import com.bacellar08.teste_cupom.domain.dto.CouponRequest;
import com.bacellar08.teste_cupom.domain.dto.CouponResponse;

public interface CouponService {

	CouponResponse createCoupon(CouponRequest request);

	CouponResponse findCoupon(String id);

	void deleteCoupon(String id);
}
