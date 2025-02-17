package com.dairyproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmPurchaseOrderDTO {

	@Email
	private String consumerEmailId;
	@Email
	private String sellerEmailId;
	private String productName;
	@NotNull
	private int quantity;
	private String paymentMode;



}
