package com.dairyproject.services;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.dairyproject.dto.ChangePasswordDTO;
import com.dairyproject.dto.LoginDTO;
import com.dairyproject.entities.ConsumerDetails;
import com.dairyproject.exceptions.ConsumerNotFoundException;
import com.dairyproject.exceptions.EmailAddressFoundException;
import com.dairyproject.exceptions.IncorrectPasswordException;
import com.dairyproject.exceptions.PhoneNumberFoundException;
import com.dairyproject.exceptions.UnmatchedPasswordException;
import com.dairyproject.exceptions.UsernameFoundException;

public interface ConsumerService {
	ConsumerDetails registerNewConsumer(ConsumerDetails consumerDetails)
			throws EmailAddressFoundException, UsernameFoundException, PhoneNumberFoundException;

	ConsumerDetails getConsumerDetailsByEmailAndPassword(String emailId, String password)
			throws UnsupportedEncodingException, ConsumerNotFoundException;

	ConsumerDetails getConsumerDetailsByUsernameAndPassword(String username, String password);

	ConsumerDetails getConsumerDetailsByPhoneNumber(String phoneNumber, String password);

	ConsumerDetails getConsumerDetailsByEmailId(String emailId);

	ConsumerDetails getConsumerDetailsByUsername(String username);

	ConsumerDetails getConsumerDetailsByPhoneNumber(String phoneNumber);

	String deleteConsumerDetailsByEmailId(LoginDTO login) throws UnsupportedEncodingException;

	String deleteConsumerDetailsByConsumerId(Integer consumerId);

	List<ConsumerDetails> getAllConsumerList();

	List<ConsumerDetails> getConsumerDetailsByFirstName(String firstName);

	List<ConsumerDetails> getConsumerListByPincode(String pincode);

	List<ConsumerDetails> getConsumerListByDistrict(String district);

	List<ConsumerDetails> getConsumerListByTown(String town);

	List<ConsumerDetails> getConsumerByAid(Integer aid);

	ConsumerDetails updateConsumerDetails(ConsumerDetails consumerDetails) throws UnsupportedEncodingException;

	String changeConsumerPassword(ChangePasswordDTO changePassword)
			throws UnsupportedEncodingException, IncorrectPasswordException, UnmatchedPasswordException;
}
