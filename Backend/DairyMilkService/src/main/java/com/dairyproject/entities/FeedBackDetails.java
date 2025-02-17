package com.dairyproject.entities;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table

@Getter
@Setter
public class FeedBackDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int FID;

	@Nullable
	@Length(min = 3, max = 18, message = "First name can be in between 3 to 18 characters only")
	@Pattern(regexp = "^[a-zA-Z ]{0,18}$", message = "Please enter your correct name")
	private String name;

	@Length(max = 50, message = "Subject cannot be more than 30 characters !")
	private String subject;

	@NotEmpty
	@Lob
	private String message;



}
