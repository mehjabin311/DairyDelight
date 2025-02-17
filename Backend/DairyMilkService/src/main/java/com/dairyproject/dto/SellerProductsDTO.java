package com.dairyproject.dto;

import java.util.Objects;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SellerProductsDTO {
	private String emailId;
	private String password;

	private Set<String> productDetails;

	@Override
	public int hashCode() {
		return Objects.hash(emailId, password, productDetails);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SellerProductsDTO)) {
			return false;
		}
		SellerProductsDTO other = (SellerProductsDTO) obj;
		return Objects.equals(emailId, other.emailId) && Objects.equals(password, other.password)
				&& Objects.equals(productDetails, other.productDetails);
	}

}
