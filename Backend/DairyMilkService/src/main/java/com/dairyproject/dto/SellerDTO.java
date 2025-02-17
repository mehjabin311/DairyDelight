package com.dairyproject.dto;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SellerDTO {
	private String emailId;
	private String firstName;
	private String lastName;
	private String town;
	private String district;
	private String pincode;

	@Override
	public int hashCode() {
		return Objects.hash(emailId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SellerDTO other = (SellerDTO) obj;
		return Objects.equals(emailId, other.emailId);
	}

}
