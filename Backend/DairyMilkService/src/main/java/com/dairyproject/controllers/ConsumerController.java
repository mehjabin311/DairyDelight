package com.dairyproject.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping; // Importing PutMapping
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dairyproject.dto.ChangePasswordDTO;
import com.dairyproject.dto.ConfirmPurchaseOrderDTO;
import com.dairyproject.dto.LoginDTO;
import com.dairyproject.dto.QueryMessageDTO;
import com.dairyproject.dto.SellerDTO;
import com.dairyproject.entities.ConsumerDetails;
import com.dairyproject.entities.FeedBackDetails;
import com.dairyproject.entities.ProductDetails;
import com.dairyproject.entities.PurchaseDetails;
import com.dairyproject.exceptions.EmailAddressFoundException;
import com.dairyproject.exceptions.IncorrectPasswordException;
import com.dairyproject.exceptions.PhoneNumberFoundException;
import com.dairyproject.exceptions.UnmatchedPasswordException;
import com.dairyproject.exceptions.UsernameFoundException;
import com.dairyproject.services.ConsumerService;
import com.dairyproject.services.FeedBackService;
import com.dairyproject.services.ProductService;
import com.dairyproject.services.PurchaseService;
import com.dairyproject.services.QueryService;
import com.dairyproject.services.SellerService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/consumer")
public class ConsumerController {

	@Autowired
	private ConsumerService conServ;

	@Autowired
	private PurchaseService purchaseServ;

	@Autowired
	private SellerService sellServ;

	@Autowired
	private FeedBackService feedServ;

	@Autowired
	private ProductService proServ;

	@Autowired
	private QueryService queryServ;

	@PostMapping("/registerdetails") // POST for creating new resources
	public ResponseEntity<?> registerNewConsumer(@Valid @RequestBody ConsumerDetails consumerDetails)
			throws EmailAddressFoundException, UsernameFoundException, PhoneNumberFoundException {
		ConsumerDetails details = conServ.registerNewConsumer(consumerDetails);
		return new ResponseEntity<>(details, HttpStatus.CREATED);
	}

	@PostMapping("/fetchdetailsbyemail") // POST for fetching details by email and password
	public ResponseEntity<?> getConsumerDetailsByEmailIdandPassword(@RequestBody LoginDTO login)
			throws UnsupportedEncodingException {
		ConsumerDetails details = conServ.getConsumerDetailsByEmailAndPassword(login.getEmailId(), login.getPassword());
		return new ResponseEntity<>(details, HttpStatus.OK);
	}

	@PutMapping("/updatedetails") // Changed to PUT for updating resources
	public ResponseEntity<?> updateConsumerDetails(@RequestBody ConsumerDetails consumerDetails)
			throws UnsupportedEncodingException {
		ConsumerDetails details = conServ.updateConsumerDetails(consumerDetails);
		return new ResponseEntity<>(details, HttpStatus.OK);
	}

	@PutMapping("/changepassword") // Changed to PUT for updating resources
	public ResponseEntity<?> changeConsumerPassword(@RequestBody ChangePasswordDTO changePassword)
			throws UnsupportedEncodingException, UnmatchedPasswordException, IncorrectPasswordException {
		String result = conServ.changeConsumerPassword(changePassword);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/removeaccount") // POST for removing account
	public ResponseEntity<?> deleteConsumerByEmailId(@RequestBody LoginDTO login) throws UnsupportedEncodingException {
		String result = conServ.deleteConsumerDetailsByEmailId(login);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/getallproducts") // GET for fetching resources
	public ResponseEntity<?> getAllProductDetails() {
		Set<ProductDetails> products = proServ.getAllProductDetails();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("/getsellersbyproductlocality") // GET for fetching resources
	public ResponseEntity<?> getSellerDetailsByProductAndLocality(@RequestParam String emailId,
			@RequestParam String productName) {
		Set<SellerDTO> sellers = sellServ.getSellersByProductAndLocality(emailId, productName);
		System.out.println(sellers);
		return new ResponseEntity<>(sellers, HttpStatus.OK);
	}

//	@GetMapping("/getpurchaserecords") // GET for fetching resources
//	public ResponseEntity<?> getPurchaseDetailsByConsumerEmailId(@RequestParam @Qualifier String emailId) {
//		List<PurchaseDetails> purchaseRecords = purchaseServ.getPurchaseDetailsByConsumerEmailId(emailId);
//		System.out.println("hiii"+purchaseRecords);
//		return new ResponseEntity<>(purchaseRecords, HttpStatus.OK);
//	}
	@GetMapping("/getpurchaserecords")
    public ResponseEntity<?> getPurchaseDetailsByConsumerEmailId(@RequestParam String emailId) {
        List<PurchaseDetails> purchaseRecords = purchaseServ.getPurchaseDetailsByConsumerEmailId(emailId);
        purchaseRecords.forEach(record -> {
            System.out.println("Purchase ID: " + record.getPurchaseId());
            System.out.println("Seller Details: " + record.getSellerDetails());
        });
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache().mustRevalidate())
                .body(purchaseRecords);
    }

	@PostMapping("/placeconfirmorder") // POST for creating new resources
	public ResponseEntity<?> purchaseConfirmOrderInRecord(@RequestBody ConfirmPurchaseOrderDTO confirmPurchaseOrder)
			throws UnsupportedEncodingException {
		PurchaseDetails purchaseDetails = purchaseServ.insertPurchaseRecord(confirmPurchaseOrder);
		return new ResponseEntity<>(purchaseDetails, HttpStatus.CREATED);

	}

	@PostMapping("/feedback/submitfeedback") // POST for creating new resources
	public ResponseEntity<?> insertNewFeedBack(@Valid @RequestBody FeedBackDetails feedBackDetails) {
		String result = feedServ.insertNewFeedBackDetails(feedBackDetails);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@PostMapping("/query/submitquery") // POST for creating new resources
	public ResponseEntity<?> insertNewConsumerQuery(@Valid @RequestBody QueryMessageDTO consumerQuery) {
		String result = queryServ.insertNewConsumerQuery(consumerQuery);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
}
