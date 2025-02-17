package com.dairyproject.services;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dairyproject.dto.ChangePassword;
import com.dairyproject.entities.Administrator;
import com.dairyproject.exceptions.IncorrectAdminDetect;
import com.dairyproject.exceptions.IncorrectPasswordException;
import com.dairyproject.exceptions.UnmatchedPasswordException;
import com.dairyproject.repositories.AdministratorRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdminServices {

	@Autowired
	private AdministratorRepository adminRepo;

	//@Autowired
	private Administrator adminDetails;

	public Administrator getAdminDetails() {
		return adminRepo.findAdminDetails();
	}

	public String changeAdminPassword(ChangePassword changePassword) throws UnsupportedEncodingException {
		adminDetails = adminRepo.findAdminDetails();
		if (adminDetails.getPassword().equals(changePassword.getOldPassword())) {
			if (changePassword.getNewPassword().equals(changePassword.getConfirmPassword())) {
				adminDetails.setPassword(changePassword.getNewPassword());
				adminRepo.save(adminDetails);
				return "Administrator Password Changed...!";
			} else {
				throw new UnmatchedPasswordException("Passoword does not match. please enter correct password");
			}
		} else {
			throw new IncorrectPasswordException("Incorrect Old Password !");
		}
	}

	public Administrator getLoginDetails(String emailId, String password) {
		adminDetails = adminRepo.findAdminDetailsByEmailIdAndPassword(emailId, password);
		if (adminDetails != null) {
			return adminDetails;
		}

		throw new IncorrectAdminDetect("Incorrect Administrator Credentials");
	}

}
