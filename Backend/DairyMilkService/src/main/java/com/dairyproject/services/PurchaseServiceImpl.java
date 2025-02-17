package com.dairyproject.services;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dairyproject.dto.ConfirmPurchaseOrderDTO;
import com.dairyproject.entities.ConsumerDetails;
import com.dairyproject.entities.ProductDetails;
import com.dairyproject.entities.PurchaseDetails;
import com.dairyproject.entities.SellerDetails;
import com.dairyproject.repositories.PurchaseRecordRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private PurchaseRecordRepository purchaseRepo;

	@Autowired
	private ConsumerService conServ;

	@Autowired
	private SellerService sellServ;

	@Autowired
	private ProductService proServ;

	@Autowired
	private PurchaseRecordRepository purRepo;

	private static long transactionId = 4567896;

	@Override
	public List<PurchaseDetails> getAllPurchaseRecords() {
		return purchaseRepo.findAllPurchaseDetails();
	}

	@Override
	public List<PurchaseDetails> getPurchaseDetailsByConsumerEmailId(String emailId) {
		entityManager.clear();
//        return purchaseRepo.findAllPurchaseDetailsByConsumerEmailId(emailId);
		List<PurchaseDetails> purchaseDetails = purchaseRepo.findAllPurchaseDetailsByConsumerEmailId(emailId);
        for (PurchaseDetails purchase : purchaseDetails) {
            purchase.setSellerDetails(sellServ.getSellerDetailsByEmailId(purchase.getSellerDetails().getEmailId()));
        }
        return purchaseDetails;
	}

	@Override
	public List<PurchaseDetails> getPurchaseDetailsBySellerEmailId(String emailId) {
		return purchaseRepo.findAllPurchaseDetailsBySellerEmailId(emailId);
	}

	@Override
	public PurchaseDetails getPurchaseDetailsByPurchaseId(Integer purchaseId) {
		return purchaseRepo.findPurchaseDetailsByPurchaseId(purchaseId);
	}

	@Override
	public PurchaseDetails insertPurchaseRecord(ConfirmPurchaseOrderDTO confirmPurchaseOrder)
			throws UnsupportedEncodingException {

		PurchaseServiceImpl.transactionId = transactionId++;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
		String dateTime = dtf.format(LocalDateTime.now());

		ProductDetails productDetails = proServ.getProductDetailsByName(confirmPurchaseOrder.getProductName());
		ConsumerDetails consumerDetails = conServ
				.getConsumerDetailsByEmailId(confirmPurchaseOrder.getConsumerEmailId());
		SellerDetails sellerDetails = sellServ.getSellerDetailsByEmailId(confirmPurchaseOrder.getSellerEmailId());
		PurchaseDetails purDetails = new PurchaseDetails();
		purDetails.setConsumerDetails(consumerDetails);
		purDetails.setSellerDetails(sellerDetails);
		purDetails.setProductDetails(productDetails);
		purDetails.setQuantity(confirmPurchaseOrder.getQuantity());
		purDetails.setTotalPrice((productDetails.getPrice() * confirmPurchaseOrder.getQuantity()));
		purDetails.setPaymentMode(confirmPurchaseOrder.getPaymentMode());
		purDetails.setTransactionId(transactionId);
		purDetails.setDateTime(dateTime);
		purDetails.setStatus("Placed");

		PurchaseDetails purchaseDetails = purchaseRepo.save(purDetails);

		productDetails = null;
		consumerDetails = null;
		sellerDetails = null;
		purDetails = null;

		return purchaseDetails;

	}

	@Override
	public void changeDeliveryStatusBySeller(Integer purchaseId, String status) {
		PurchaseDetails purchaseDetails = purRepo.findPurchaseDetailsByPurchaseId(purchaseId);
		purchaseDetails.setStatus(status);
		purchaseRepo.save(purchaseDetails);
	}
}
