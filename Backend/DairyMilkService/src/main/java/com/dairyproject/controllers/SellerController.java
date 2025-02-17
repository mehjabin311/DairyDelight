package com.dairyproject.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping; // Importing PutMapping
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dairyproject.dto.ChangePasswordDTO;
import com.dairyproject.dto.LoginByPhone;
import com.dairyproject.dto.LoginByUsername;
import com.dairyproject.dto.LoginDTO;
import com.dairyproject.dto.QueryMessageDTO;
import com.dairyproject.dto.SellerProductsDTO;
import com.dairyproject.entities.FeedBackDetails;
import com.dairyproject.entities.ProductDetails;
import com.dairyproject.entities.PurchaseDetails;
import com.dairyproject.entities.SellerDetails;
import com.dairyproject.exceptions.EmailAddressFoundException;
import com.dairyproject.exceptions.IncorrectPasswordException;
import com.dairyproject.exceptions.PhoneNumberFoundException;
import com.dairyproject.exceptions.ProductNotFoundException;
import com.dairyproject.exceptions.SellerNotFoundException;
import com.dairyproject.exceptions.UnmatchedPasswordException;
import com.dairyproject.exceptions.UsernameFoundException;
import com.dairyproject.services.FeedBackService;
import com.dairyproject.services.PurchaseService;
import com.dairyproject.services.QueryService;
import com.dairyproject.services.SellerService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/seller")
public class SellerController {

	@Autowired
	private SellerService sellServ;

	@Autowired
	private PurchaseService purchaseServ;

	@Autowired
	private QueryService queryServ;

	@Autowired
	private FeedBackService feedServ;

	@PostMapping("/registerdetails")
	public ResponseEntity<?> registeredNewSeller(@Valid @RequestBody SellerDetails sellerDetails)
			throws EmailAddressFoundException, UsernameFoundException, PhoneNumberFoundException {
		SellerDetails details = sellServ.registerNewSeller(sellerDetails);
		return new ResponseEntity<>(details, HttpStatus.CREATED);
	}

	@PostMapping("/fetchdetailsbyemail")
	public ResponseEntity<?> getSellerDetailsByEmailIdandPassword(@RequestBody LoginDTO login)
			throws UnsupportedEncodingException {
		SellerDetails details = sellServ.getSellerDetailsByEmailAndPassword(login.getEmailId(), login.getPassword());
		return new ResponseEntity<>(details, HttpStatus.OK);
	}

	@PostMapping("/fetchdetailsbyusername")
	public ResponseEntity<?> getSellerDetailsByUsernameAndPassword(@RequestBody LoginByUsername loginByUsername) {
		SellerDetails details = sellServ.getSellerDetailsByUsernameAndPassword(loginByUsername.getUsername(),
				loginByUsername.getPassword());
		return new ResponseEntity<>(details, HttpStatus.OK);
	}

	@PostMapping("/fetchdetailsbyphonenumber")
	public ResponseEntity<?> getSellerDetailsByPhoneNumberAndPassword(@RequestBody LoginByPhone loginByPhone) {
		SellerDetails details = sellServ.getSellerDetailsByPhoneNumberAndPassword(loginByPhone.getPhoneNumber(),
				loginByPhone.getPassword());
		return new ResponseEntity<>(details, HttpStatus.OK);
	}

	@PutMapping("/updatedetails") // Changed to PUT for updating resources
	public ResponseEntity<?> updateSellerDetails(@RequestBody SellerDetails sellerDetails) {
		SellerDetails details = sellServ.updateSellerDetails(sellerDetails);
		return new ResponseEntity<>(details, HttpStatus.OK);
	}

	@PutMapping("/changepassword") // Changed to PUT for updating resources
	public ResponseEntity<?> changeSellerPassword(@RequestBody ChangePasswordDTO changePassword)
			throws UnsupportedEncodingException, IncorrectPasswordException, UnmatchedPasswordException {
		String result = sellServ.changeSellerPassword(changePassword);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/removeaccount")
	public ResponseEntity<?> deleteSellerByEmailId(@RequestBody LoginDTO login) throws UnsupportedEncodingException {
		String result = sellServ.deleteSellerDetailsByEmailId(login);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/fetchnearbysellers")
	public ResponseEntity<?> getSellersByLocality(@RequestParam String emailId) {
		Set<SellerDetails> sellers = sellServ.getSellerListByLocality(emailId);
		return new ResponseEntity<>(sellers, HttpStatus.OK);
	}

	@PostMapping("/products/addproducts")
	public ResponseEntity<?> addProducts(@RequestBody SellerProductsDTO sellerProducts)
			throws ProductNotFoundException, SellerNotFoundException {
		Set<ProductDetails> products = sellServ.addProducts(sellerProducts);
		return new ResponseEntity<>(products, HttpStatus.CREATED);
	}

	@GetMapping("/products/getallproducts")
	public ResponseEntity<?> getSellerAllProductDetails(@RequestParam String emailId) throws SellerNotFoundException {
		Set<ProductDetails> products = sellServ.getSellerAllProductDetails(emailId);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("/product/removeproduct")
	public ResponseEntity<?> removeProductByProductName(@RequestParam String emailId, @RequestParam Integer pid)
			throws ProductNotFoundException {
		String result = sellServ.removeProductFromList(emailId, pid);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/getpurchaserecords")
	public ResponseEntity<?> getPurchaseDetailsBySellerEmailId(@RequestParam String emailId) {
		List<PurchaseDetails> purchaseRecords = purchaseServ.getPurchaseDetailsBySellerEmailId(emailId);
		return new ResponseEntity<>(purchaseRecords, HttpStatus.OK);
	}

	@PutMapping("/changedeliverystatus/{purchaseId}/{status}") // Changed to PUT for updating resources
	public ResponseEntity<?> changeDeliveryStatus(@PathVariable int purchaseId, @PathVariable String status) {
		purchaseServ.changeDeliveryStatusBySeller(purchaseId, status);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/feedback/submitfeedback")
	public ResponseEntity<?> insertNewFeedBack(@Valid @RequestBody FeedBackDetails feedBackDetails) {
		String result = feedServ.insertNewFeedBackDetails(feedBackDetails);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@PostMapping("/query/submitquery")
	public ResponseEntity<?> insertNewSellerQuery(@Valid @RequestBody QueryMessageDTO sellerQuery) {
		String result = queryServ.insertNewSellerQuery(sellerQuery);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
}
