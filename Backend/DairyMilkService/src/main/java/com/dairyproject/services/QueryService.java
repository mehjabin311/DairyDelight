package com.dairyproject.services;

import java.util.List;

import com.dairyproject.dto.QueryMessageDTO;
import com.dairyproject.entities.ConsumerQuery;
import com.dairyproject.entities.SellerQuery;

public interface QueryService {
	String insertNewConsumerQuery(QueryMessageDTO consumerQuery);

	List<ConsumerQuery> getAllConsumerQueries();

	List<ConsumerQuery> getConsumerQueriesByDateTime(String dateTime);

	List<ConsumerQuery> getConsumerQueriesByConsumerEmailId(String emailId);

	String insertNewSellerQuery(QueryMessageDTO sellerQuery);

	List<SellerQuery> getAllSellerQueries();

	List<SellerQuery> getSellerQueriesByDateTime(String dateTime);

	List<SellerQuery> getSellerQueriesBySellerEmailId(String emailId);
}
