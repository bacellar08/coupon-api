package com.bacellar08.teste_cupom.domain;

import com.bacellar08.teste_cupom.exception.BusinessException;
import com.bacellar08.teste_cupom.exception.MessageException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Coupon {

	@Id
	private String id;

	@Embedded
	private CouponCode code;

	@Column(nullable = false)
	private String description;

	@Embedded
	private Discount discountValue;

	@Column(nullable = false)
	private Instant expirationDate;

	@Enumerated(EnumType.STRING)
	private StatusEnum status;

	private boolean published;

	private boolean redeemed;

	@Builder
	public Coupon(CouponCode code, String description, Discount discountValue, Instant expirationDate,
				  StatusEnum status, boolean published, boolean redeemed) {
		this.code = code;
		this.description = description;
		this.discountValue = discountValue;
		this.expirationDate = expirationDate;
		this.status = status;
		this.published = published;
		this.redeemed = redeemed;
	}

	public void delete() {
		if (this.status == StatusEnum.DELETED) {
			throw new BusinessException(MessageException.builder()
					.code("CPN-007")
					.title("Invalid operation")
					.detail("Coupon is already deleted")
					.build());
		}

		this.status = StatusEnum.DELETED;
	}

	@PrePersist
	private void generateId() {
		if (this.id == null) {
			this.id = newId();
		}
	}

	public static String newId() {
		return "urn:coupon:" + UUID.randomUUID();
	}
}
