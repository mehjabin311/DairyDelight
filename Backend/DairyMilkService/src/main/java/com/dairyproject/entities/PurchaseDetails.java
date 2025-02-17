package com.dairyproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@ToString
@Getter
@Setter
public class PurchaseDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int purchaseId;

	@ManyToOne
	@JoinColumn(name = "consumerId")
	private ConsumerDetails consumerDetails;

	@ManyToOne
	@JoinColumn(name = "sellerId")
	private SellerDetails sellerDetails;

	@ManyToOne
	@JoinColumn(name = "PID")
	private ProductDetails productDetails;

	@NotNull(message = "Quantity cannot be empty !")
	private int quantity;

	@NotNull(message = "Total Price cannot be empty !")
	private float totalPrice;

	@NotEmpty(message = "Select proper payment mode.")
	@Pattern(regexp = "^(UPI|Credit|Debit|COD)$", message = "Incorrect payment method")
	private String paymentMode;

	@NotNull(message = "Transactional ID cannot be null !")
	private long transactionId;

	private String dateTime;

	@NotEmpty(message = "Purchase Status cannot be empty !")
	@Pattern(regexp = "^(Placed|Shipped|Delivered)$", message = "Improper purchase status !")
	private String status;


}
