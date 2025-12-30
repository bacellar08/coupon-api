package com.bacellar08.teste_cupom.controller;

import com.bacellar08.teste_cupom.domain.dto.CouponRequest;
import com.bacellar08.teste_cupom.domain.dto.CouponResponse;
import com.bacellar08.teste_cupom.exception.MessageException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("v1/coupon")
public interface CouponController {

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(
			summary = "Register a new coupon",
			description = "Registers a new coupon in the system",
			tags = "Coupon",
			responses = {
					@ApiResponse(responseCode = "201", description = "Created",
							content = @Content(schema = @Schema(implementation = CouponResponse.class))),
					@ApiResponse(responseCode = "422", description = "Business rule violation",
							content = @Content(schema = @Schema(implementation = MessageException.class))),
					@ApiResponse(responseCode = "400", description = "Invalid request",
							content = @Content(schema = @Schema(implementation = MessageException.class))),
					@ApiResponse(responseCode = "500", description = "Internal server error",
							content = @Content(schema = @Schema(implementation = MessageException.class)))
			}
	)
	ResponseEntity<CouponResponse> createCoupon(@RequestBody CouponRequest coupon);

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(
			summary = "Find a coupon",
			description = "Finds a coupon by its id",
			tags = "Coupon",
			responses = {
					@ApiResponse(responseCode = "200", description = "Ok",
							content = @Content(schema = @Schema(implementation = CouponResponse.class))),
					@ApiResponse(responseCode = "400", description = "Invalid request",
							content = @Content(schema = @Schema(implementation = MessageException.class))),
					@ApiResponse(responseCode = "500", description = "Internal server error",
							content = @Content(schema = @Schema(implementation = MessageException.class)))
			}
	)
	ResponseEntity<CouponResponse> findCoupon(@PathVariable("id") String id);

	@DeleteMapping(path = "/{id}")
	@Operation(
			summary = "Delete a coupon",
			description = "Deletes a coupon by its id",
			tags = "Coupon",
			responses = {
					@ApiResponse(responseCode = "204", description = "No Content")
			}
	)
	ResponseEntity<Void> deleteCoupon(@PathVariable("id") String id);

}
