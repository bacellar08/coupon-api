package com.bacellar08.teste_cupom.domain;

import com.bacellar08.teste_cupom.exception.BusinessException;
import com.bacellar08.teste_cupom.exception.MessageException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Embeddable
@Getter
public class Discount {

	@Column(name = "discount_value", nullable = false)
	private BigDecimal discountValue;


	public Discount(BigDecimal value) {

		if (value == null) {
			throw new BusinessException(MessageException.builder()
					.code("CPN-002")
					.title("Invalid discount value")
					.detail("Discount value cannot be null")
					.build());
		}

		if (value.compareTo(BigDecimal.valueOf(0.5)) < 0) {
			throw new BusinessException(MessageException.builder()
					.code("CPN-002")
					.title("Invalid discount value")
					.detail("Discount value must be at least 0.5")
					.build());
		}
		this.discountValue = value;
	}
}
