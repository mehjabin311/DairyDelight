package com.dairyproject.entities;

import java.util.Set;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table

@Getter
@Setter
@ToString(exclude = "productDetails")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "sellerId")
public class SellerDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sellerId;

	@NotEmpty(message = "First Name is required")
	@Length(min = 3, max = 18, message = "First name can be in between 3 to 18 characters only")
	@Pattern(regexp = "^[a-zA-Z]{3,18}$", message = "Please enter your correct name")
	private String firstName;

	@Length(max = 18, message = "First name can be in between 3 to 18 characters only")
	@Pattern(regexp = "^[a-zA-Z]{0,18}$", message = "Please enter your correct last name ")
	private String lastName;

	@NotEmpty(message = "Select your gender")
	@Pattern(regexp = "^(Male|Female|Other)?", message = "Choose gender in between Male, Female, Other")
	private String gender;

	@NotNull(message = "Age is required, please enter your current age")
	@Range(min = 16, max = 60, message = "You are not eligible for this job")
	private int age;

	@NotEmpty(message = "Email address is required for registration")
	@Column(unique = true)
	@Email(regexp = "^\\w+[\\w-\\.]*\\@\\w+((-\\w+)|(\\w*))\\.[a-z]{2,3}$", message = "Please enter your valid email address")
	private String emailId;

	@NotEmpty(message = "Please enter your phone number")
	@Column(length = 14, unique = true)
	@Length(min = 10, max = 10, message = "Please enter correct phone number")
	@Pattern(regexp = "^[6789][0-9]{9}$", message = "Please enter correct phone number")
	private String phoneNumber;

	@Length(max = 40, message = "Street cannot be more than 40 characters")
	private String street;

	@NotEmpty(message = "Username must be required for registration and login")
	@Length(min = 5, max = 15, message = "Username must be in between 5 to 15 characters")
	@Column(unique = true)
	@Pattern(regexp = "^[a-zA-Z0-9_-]{5,15}$", message = "Please enter correct username")
	private String username;

	@NotEmpty(message = "Password is required")
//	@Length(min = 6, max = 12, message = "Password must be in between 6 to 12 characters")
//	@Pattern(regexp = "(?=^.{6,15}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&amp;*()_+}{&quot;:;'?/&gt;.&lt;,])(?!.*\\s).*$", message = "password must contain atleast 1 uppercase, 1 lowercase, 1 special character and 1 digit ")
	private String password;

	@OneToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "AID")
	private AddressDetails address;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "seller_product_details", joinColumns = @JoinColumn(name = "sellerId"), inverseJoinColumns = @JoinColumn(name = "productId"))
	@JsonIgnore
	private Set<ProductDetails> productDetails;

	public void removeProduct(ProductDetails product) {
		if (this.productDetails != null) {
			this.productDetails.remove(product);
			product.getSellerDetails().remove(this); // Remove seller from the product's set
		}
	}

}
