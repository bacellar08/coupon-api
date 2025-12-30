package com.bacellar08.teste_cupom.domain;

import com.bacellar08.teste_cupom.exception.BusinessException;
import com.bacellar08.teste_cupom.exception.MessageException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class CouponCode {

	@Column(name = "code_value", length = 6, nullable = false)
	private String codeValue;

	public CouponCode(String code) {

		if (code == null || code.isBlank()) {
			throw new BusinessException(MessageException.builder()
					.code("CPN-001")
					.title("Invalid coupon code")
					.detail("Coupon code cannot be null or blank")
					.build());
		}

		String fromattedCode = code.replaceAll("[^a-zA-Z0-9]", "");
		if (fromattedCode.length() != 6) {
			throw new BusinessException(MessageException.builder()
					.code("CPN-001")
					.title("Invalid coupon code")
					.detail("Coupon code must have exactly 6 alphanumeric characters")
					.build());
		}
		this.codeValue = fromattedCode.toUpperCase();
	}
}
