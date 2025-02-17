package com.dairyproject.services;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.dairyproject.dto.ConfirmPurchaseOrderDTO;
import com.dairyproject.entities.PurchaseDetails;

public interface PurchaseService {
	List<PurchaseDetails> getAllPurchaseRecords();

	List<PurchaseDetails> getPurchaseDetailsByConsumerEmailId(String emailId);

	List<PurchaseDetails> getPurchaseDetailsBySellerEmailId(String emailId);

	PurchaseDetails getPurchaseDetailsByPurchaseId(Integer purchaseId);

	PurchaseDetails insertPurchaseRecord(ConfirmPurchaseOrderDTO confirmPurchaseOrder) throws UnsupportedEncodingException;

	void changeDeliveryStatusBySeller(Integer purchaseId, String status);
}
