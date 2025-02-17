package com.dairyproject.services;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dairyproject.dto.ChangePasswordDTO;
import com.dairyproject.dto.LoginDTO;
import com.dairyproject.entities.AddressDetails;
import com.dairyproject.entities.ConsumerDetails;
import com.dairyproject.exceptions.ConsumerNotFoundException;
import com.dairyproject.exceptions.EmailAddressFoundException;
import com.dairyproject.exceptions.IncorrectPasswordException;
import com.dairyproject.exceptions.PhoneNumberFoundException;
import com.dairyproject.exceptions.UnmatchedPasswordException;
import com.dairyproject.exceptions.UsernameFoundException;
import com.dairyproject.repositories.AddressRepository;
import com.dairyproject.repositories.ConsumerRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ConsumerServiceImpl implements ConsumerService {

	@Autowired
	private ConsumerRepository conRepo;

	@Autowired
	private AddressRepository addRepo;

	@Autowired
	private DeletedRecordsService delServ;

	private AddressDetails addressDetails;
	private ConsumerDetails conDetails;

	@Override
	public ConsumerDetails registerNewConsumer(ConsumerDetails consumerDetails)
			throws EmailAddressFoundException, UsernameFoundException, PhoneNumberFoundException {
		int email = conRepo.findConsumerDetailsByEmailId(consumerDetails.getEmailId());
		int username = conRepo.findConsumerDetailsByUsername(consumerDetails.getUsername());
		int phoneNumber = conRepo.findConsumerDetailsByPhoneNumber(consumerDetails.getPhoneNumber());

		//addressDetails = addRepo.findAddressDetailsByPincode(consumerDetails.getAddress().getPincode());
		String encryptPassword = Base64.getEncoder().encodeToString(consumerDetails.getPassword().getBytes());
		consumerDetails.setPassword(encryptPassword);
		if (email == 1) {
			throw new EmailAddressFoundException("Email address already registered");
		} else if (username == 1) {
			throw new UsernameFoundException("Username already taken, please try with another one");
		} else if (phoneNumber == 1) {
			throw new PhoneNumberFoundException("Phone Number already registered");
		} else {
			conRepo.save(consumerDetails);
			return conRepo.findConsumerDetailsByEmailAndPassword(consumerDetails.getEmailId(),
					consumerDetails.getPassword());
		}

	}
//	 else if (addressDetails != null) {
//			consumerDetails.setAddress(addressDetails);
//			conRepo.save(consumerDetails);
//			addressDetails = null;
//			return conRepo.findConsumerDetailsByEmailAndPassword(consumerDetails.getEmailId(),
//					consumerDetails.getPassword());
//		}

	@Override
	public ConsumerDetails getConsumerDetailsByEmailAndPassword(String emailId, String password)
			throws UnsupportedEncodingException, ConsumerNotFoundException {
		String encryptPassword = Base64.getEncoder().encodeToString(password.getBytes("UTF-8"));
		conDetails = conRepo.findConsumerDetailsByEmailAndPassword(emailId, encryptPassword);
		if (conDetails != null) {
			return conDetails;
		}
		throw new ConsumerNotFoundException("Consumer not found !");
	}

	@Override
	public ConsumerDetails getConsumerDetailsByUsernameAndPassword(String username, String password) {
		return conRepo.findConsumerDetailsByUsernameAndPassword(username, password);
	}

	@Override
	public ConsumerDetails getConsumerDetailsByPhoneNumber(String phoneNumber, String password) {
		return conRepo.findConsumerDetailsByPhoneNumberAndPassword(phoneNumber, password);
	}

	@Override
	public ConsumerDetails getConsumerDetailsByEmailId(String emailId) {
		return conRepo.findConsumerDetailsByEmailIdOnly(emailId);
	}

	@Override
	public ConsumerDetails getConsumerDetailsByUsername(String username) {
		return conRepo.findConsumerDetailsByUsernameOnly(username);
	}

	@Override
	public ConsumerDetails getConsumerDetailsByPhoneNumber(String phoneNumber) {
		return conRepo.findConsumerDetailsByPhoneNumberOnly(phoneNumber);
	}

	@Override
	public String deleteConsumerDetailsByEmailId(LoginDTO login) throws UnsupportedEncodingException {
		return delServ.deleteConsumerByEmailId(login);
	}

	@Override
	public String deleteConsumerDetailsByConsumerId(Integer consumerId) {
		return delServ.deleteConsumerByConsumerId(consumerId);
	}

	@Override
	public List<ConsumerDetails> getAllConsumerList() {
		return conRepo.findAllConsumerDetails();
	}

	@Override
	public List<ConsumerDetails> getConsumerDetailsByFirstName(String firstName) {
		return conRepo.findConsumerByName(firstName);
	}

	@Override
	public List<ConsumerDetails> getConsumerListByPincode(String pincode) {
		return conRepo.findConsumersByPincode(pincode);
	}

	@Override
	public List<ConsumerDetails> getConsumerListByDistrict(String district) {
		return conRepo.findConsumersByDistrict(district);
	}

	@Override
	public List<ConsumerDetails> getConsumerListByTown(String town) {
		return conRepo.findConsumersByTown(town);
	}

	@Override
	public List<ConsumerDetails> getConsumerByAid(Integer aid) {
		return conRepo.findConsumerDetailsByAid(aid);
	}

	@Override
	public ConsumerDetails updateConsumerDetails(ConsumerDetails consumerDetails) throws UnsupportedEncodingException {
		ConsumerDetails conDetails = conRepo.findConsumerDetailsByEmailIdOnly(consumerDetails.getEmailId());

		addressDetails = addRepo.findAddressDetailsByPincode(consumerDetails.getAddress().getPincode());
//		if (addressDetails != null) {
//			conDetails.setAddress(addressDetails);
//		} else {
		addRepo.save(consumerDetails.getAddress());
		addressDetails = addRepo.findAddressDetailsByPincode(consumerDetails.getAddress().getPincode());
		conDetails.setAddress(addressDetails);
//		}

		conDetails.setStreet(consumerDetails.getStreet());
		conDetails.setFirstName(consumerDetails.getFirstName());
		conDetails.setLastName(consumerDetails.getLastName());
		conDetails.setPhoneNumber(consumerDetails.getPhoneNumber());
		conRepo.save(conDetails);
		conDetails = null;

		return conRepo.findConsumerDetailsByEmailAndPassword(consumerDetails.getEmailId(),
				consumerDetails.getPassword());

	}

	@Override
	public String changeConsumerPassword(ChangePasswordDTO changePassword)
			throws UnsupportedEncodingException, IncorrectPasswordException, UnmatchedPasswordException {

		if (changePassword.getNewPassword().equals(changePassword.getConfirmPassword())) {
			String encryptPassword = Base64.getEncoder()
					.encodeToString(changePassword.getOldPassword().getBytes("UTF-8"));
			String newEncryptPassword = Base64.getEncoder()
					.encodeToString(changePassword.getNewPassword().getBytes("UTF-8"));
			conDetails = conRepo.findConsumerDetailsByEmailAndPassword(changePassword.getEmailId(), encryptPassword);
			if (conDetails != null && conDetails.getPassword().equals(encryptPassword)) {
				conDetails.setPassword(newEncryptPassword);
				conRepo.save(conDetails);
				conDetails = null;
				return "Password changed successfully.";
			} else {
				throw new IncorrectPasswordException("Incorrect old password !");
			}

		} else {
			throw new UnmatchedPasswordException("Password does not match. Please enter correct password !");
		}

	}
}
