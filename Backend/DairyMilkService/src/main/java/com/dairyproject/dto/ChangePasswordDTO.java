package com.dairyproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter

@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDTO {
	private String emailId;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
}
