package com.dairyproject.services;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dairyproject.dto.LoginDTO;
import com.dairyproject.entities.ConsumerDetails;
import com.dairyproject.entities.DeletedConsumerRecords;
import com.dairyproject.entities.DeletedSellerRecords;
import com.dairyproject.entities.ProductDetails;
import com.dairyproject.entities.SellerDetails;
import com.dairyproject.repositories.ConsumerRepository;
import com.dairyproject.repositories.DeletedConsumerRepository;
import com.dairyproject.repositories.DeletedSellerRepository;
import com.dairyproject.repositories.SellerRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DeletedRecordsServiceImpl implements DeletedRecordsService {

	@Autowired
	private ConsumerRepository conRepo;

	@Autowired
	private DeletedConsumerRepository delConsumerRepo;

	@Autowired
	private SellerRepository sellRepo;

	@Autowired
	private DeletedSellerRepository delSellRepo;
	
//	@Autowired
//	private AddressService addressService;

	private ConsumerDetails consumerDetails;
	private SellerDetails sellDetails;
	private DeletedConsumerRecords delConRecord = new DeletedConsumerRecords();
	private DeletedSellerRecords delSellRecord = new DeletedSellerRecords();

	@Override
	public String deleteConsumerByEmailId(LoginDTO login) throws UnsupportedEncodingException {
		String encryptedPassword = Base64.getEncoder().encodeToString(login.getPassword().getBytes("UTF-8"));
		consumerDetails = conRepo.findConsumerDetailsByEmailAndPassword(login.getEmailId(), encryptedPassword);
		if (consumerDetails != null && conRepo.deleteConsumerDetailsByEmailId(consumerDetails.getEmailId()) == 1) {
			delConRecord.setAddress(consumerDetails.getAddress());
			delConRecord.setEmailId(consumerDetails.getEmailId());
			delConRecord.setFirstName(consumerDetails.getFirstName());
			delConRecord.setLastName(consumerDetails.getLastName());
			delConRecord.setGender(consumerDetails.getGender());
			delConRecord.setPhoneNumber(consumerDetails.getPhoneNumber());
			delConRecord.setConsumerId(consumerDetails.getConsumerId());
			delConRecord.setStreet(consumerDetails.getStreet());
			delConRecord.setUsername(consumerDetails.getUsername());
			delConsumerRepo.save(delConRecord);
			consumerDetails = null;
			delConRecord = null;
			return "Consumer account removed !";
		}

		return "Account not found !";
	}

	@Override
	public String deleteConsumerByConsumerId(Integer consumerId) {
		consumerDetails = conRepo.findConsumerDetailsByConsumerId(consumerId);
		System.out.println(consumerDetails.getAddress());
		if (consumerDetails != null
				&& conRepo.deleteConsumerDetailsByConsumerId(consumerDetails.getConsumerId()) == 1) {
			delConRecord.setAddress(consumerDetails.getAddress());
			delConRecord.setEmailId(consumerDetails.getEmailId());
			delConRecord.setFirstName(consumerDetails.getFirstName());
			delConRecord.setLastName(consumerDetails.getLastName());
			delConRecord.setGender(consumerDetails.getGender());
			delConRecord.setPhoneNumber(consumerDetails.getPhoneNumber());
			delConRecord.setConsumerId(consumerDetails.getConsumerId());
			delConRecord.setStreet(consumerDetails.getStreet());
			delConRecord.setUsername(consumerDetails.getUsername());
			delConsumerRepo.save(delConRecord);
			consumerDetails = null;
			// delConRecord = null;
			return "Consumer account removed !";
		}
		return "Account not found !";
	}

	@Override
	public List<DeletedConsumerRecords> getDeletedAllConsumerRecords() {
		return delConsumerRepo.findAllDeletedConsumerRecords();
	}

	@Override
	public List<DeletedSellerRecords> getDeletedAllSellerRecords() {
		return delSellRepo.findAllDeletedSellerRecords();
	}

	@Override
	public DeletedConsumerRecords getDeletedConsumerRecordByEmailId(String emailId) {
		return delConsumerRepo.findDeletedConsumerByEmailId(emailId);
	}

	@Override
	public List<DeletedConsumerRecords> getDeletedConsumerRecordsByFirstName(String name) {
		return delConsumerRepo.findDeletedConsumerRecordsByFirstName(name);
	}

	@Override
	public String deleteSellerByEmailId(LoginDTO login) throws UnsupportedEncodingException {
		String encryptedPassword = Base64.getEncoder().encodeToString(login.getPassword().getBytes("UTF-8"));
		sellDetails = sellRepo.findSellerDetailsByEmailAndPassword(login.getEmailId(), encryptedPassword);
		Set<ProductDetails> prodList = sellDetails.getProductDetails();
		if (sellDetails != null) {
			delSellRecord.setAddress(sellDetails.getAddress());
			delSellRecord.setEmailId(sellDetails.getEmailId());
			delSellRecord.setFirstName(sellDetails.getFirstName());
			delSellRecord.setLastName(sellDetails.getLastName());
			delSellRecord.setGender(sellDetails.getGender());
			delSellRecord.setPhoneNumber(sellDetails.getPhoneNumber());
			delSellRecord.setSellerId(sellDetails.getSellerId());
			delSellRecord.setStreet(sellDetails.getStreet());
			delSellRecord.setUsername(sellDetails.getUsername());
			delSellRepo.save(delSellRecord);
			for (ProductDetails product : prodList) {
				sellDetails.removeProduct(product);
			}
			sellRepo.deleteSellerDetailsBySellerId(sellDetails.getSellerId());
			sellDetails = null;
			return "Seller account removed !";
		}
//        && sellRepo.deleteSellerDetailsByEmailId(sellDetails.getEmailId()) == 1
		return "Account not found !";
	}

	@Override
	public String deleteSellerBySellerId(int sellerId) {
		sellDetails = sellRepo.findSellerDetailsBySellerId(sellerId);
		System.out.println(sellDetails);
		if (sellDetails != null) {
			delSellRecord.setAddress(sellDetails.getAddress());
			sellRepo.deleteSellerDetailsBySellerId(sellDetails.getSellerId());
			delSellRecord.setEmailId(sellDetails.getEmailId());
			delSellRecord.setFirstName(sellDetails.getFirstName());
			delSellRecord.setLastName(sellDetails.getLastName());
			delSellRecord.setAge(sellDetails.getAge());
			delSellRecord.setGender(sellDetails.getGender());
			delSellRecord.setPhoneNumber(sellDetails.getPhoneNumber());
			delSellRecord.setSellerId(sellDetails.getSellerId());
			delSellRecord.setStreet(sellDetails.getStreet());
			delSellRecord.setUsername(sellDetails.getUsername());
			delSellRepo.save(delSellRecord);
			consumerDetails = null;
			// delSellRecord = null;
			return "Seller account removed !";
		}
		return "Account not found !";
	}

//	@Transactional
//	@Override
//	public String deleteSellerBySellerId(int sellerId) {
//		sellDetails = sellRepo.findSellerDetailsBySellerId(sellerId);
//		if (sellDetails != null) {
//			// If seller has products, remove the seller from the products first
//			if (sellDetails.getProductDetails() != null) {
//				sellDetails.getProductDetails().forEach(product -> {
//					product.getSellerDetails().remove(sellDetails);
//				});
//			}
//
//			// Delete seller
//			sellRepo.deleteSellerDetailsBySellerId(sellDetails.getSellerId());
//
//			// Optionally, save the deleted record
//			delSellRecord.setAddress(sellDetails.getAddress());
//			delSellRecord.setEmailId(sellDetails.getEmailId());
//			delSellRecord.setFirstName(sellDetails.getFirstName());
//			delSellRecord.setLastName(sellDetails.getLastName());
//			delSellRecord.setAge(sellDetails.getAge());
//			delSellRecord.setGender(sellDetails.getGender());
//			delSellRecord.setPhoneNumber(sellDetails.getPhoneNumber());
//			delSellRecord.setSellerId(sellDetails.getSellerId());
//			delSellRecord.setStreet(sellDetails.getStreet());
//			delSellRecord.setUsername(sellDetails.getUsername());
//			delSellRepo.save(delSellRecord);
//
//			return "Seller account removed!";
//		}
//		return "Account not found!";
//	}

	@Override
	public DeletedSellerRecords getDeletedSellerRecordByEmailId(String emailId) {
		return delSellRepo.findDeletedSellerRecordsByEmailId(emailId);
	}

	@Override
	public List<DeletedSellerRecords> getDeletedSellerRecordsByFirstName(String name) {
		return delSellRepo.findDeletedSellerRecordsByFirstName(name);
	}
}
