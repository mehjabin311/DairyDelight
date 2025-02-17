package com.dairyproject.services;

import java.io.UnsupportedEncodingException;

import com.dairyproject.dto.ChangePasswordDTO;
import com.dairyproject.entities.Administrator;
import com.dairyproject.exceptions.IncorrectAdminDetect;
import com.dairyproject.exceptions.IncorrectPasswordException;
import com.dairyproject.exceptions.UnmatchedPasswordException;

public interface AdminService {
	Administrator getAdminDetails();

	String changeAdminPassword(ChangePasswordDTO changePassword)
			throws UnsupportedEncodingException, IncorrectPasswordException, UnmatchedPasswordException;

	Administrator getLoginDetails(String emailId, String password) throws IncorrectAdminDetect;
}
