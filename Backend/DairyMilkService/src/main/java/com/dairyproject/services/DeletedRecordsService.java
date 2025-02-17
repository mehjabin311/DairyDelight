package com.dairyproject.services;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.dairyproject.dto.LoginDTO;
import com.dairyproject.entities.DeletedConsumerRecords;
import com.dairyproject.entities.DeletedSellerRecords;

public interface DeletedRecordsService {
	String deleteConsumerByEmailId(LoginDTO login) throws UnsupportedEncodingException;

	String deleteConsumerByConsumerId(Integer consumerId);

	List<DeletedConsumerRecords> getDeletedAllConsumerRecords();

	List<DeletedSellerRecords> getDeletedAllSellerRecords();

	DeletedConsumerRecords getDeletedConsumerRecordByEmailId(String emailId);

	List<DeletedConsumerRecords> getDeletedConsumerRecordsByFirstName(String name);

	String deleteSellerByEmailId(LoginDTO login) throws UnsupportedEncodingException;

	String deleteSellerBySellerId(int sellerId);

	DeletedSellerRecords getDeletedSellerRecordByEmailId(String emailId);

	List<DeletedSellerRecords> getDeletedSellerRecordsByFirstName(String name);
}
