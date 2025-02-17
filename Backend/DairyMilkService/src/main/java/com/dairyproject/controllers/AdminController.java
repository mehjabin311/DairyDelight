package com.dairyproject.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dairyproject.dto.ChangePasswordDTO;
import com.dairyproject.dto.LoginDTO;
import com.dairyproject.entities.Administrator;
import com.dairyproject.entities.ConsumerDetails;
import com.dairyproject.entities.ConsumerQuery;
import com.dairyproject.entities.DeletedConsumerRecords;
import com.dairyproject.entities.DeletedSellerRecords;
import com.dairyproject.entities.FeedBackDetails;
import com.dairyproject.entities.ProductDetails;
import com.dairyproject.entities.PurchaseDetails;
import com.dairyproject.entities.SellerDetails;
import com.dairyproject.entities.SellerQuery;
import com.dairyproject.exceptions.IncorrectAdminDetect;
import com.dairyproject.exceptions.IncorrectPasswordException;
import com.dairyproject.exceptions.ProductNotFoundException;
import com.dairyproject.exceptions.UnmatchedPasswordException;
import com.dairyproject.services.AdminService;
import com.dairyproject.services.ConsumerService;
import com.dairyproject.services.DeletedRecordsService;
import com.dairyproject.services.FeedBackService;
import com.dairyproject.services.ProductService;
import com.dairyproject.services.PurchaseService;
import com.dairyproject.services.QueryService;
import com.dairyproject.services.SellerService;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminServ;

	@Autowired
	private ConsumerService conServ;

	@Autowired
	private SellerService sellServ;

	@Autowired
	private DeletedRecordsService delServ;

	@Autowired
	private FeedBackService feedServ;

	@Autowired
	private QueryService queryServ;

	@Autowired
	private ProductService proServ;

	@Autowired
	private PurchaseService purchaseServ;

	@GetMapping("/fetchconsumerbyemail")
	public ResponseEntity<?> getConsumerDetailsByEmailId(@RequestParam String emailId) {
		ConsumerDetails details = conServ.getConsumerDetailsByEmailId(emailId);
		return new ResponseEntity<>(details, HttpStatus.OK);
	}

	@GetMapping("/fetchconsumerbyusername")
	public ResponseEntity<?> getConsumerDetailsByUsername(@RequestParam String username) {
		ConsumerDetails details = conServ.getConsumerDetailsByUsername(username);
		return new ResponseEntity<>(details, HttpStatus.OK);
	}

	@GetMapping("/fetchconsumerbyphone")
	public ResponseEntity<?> getConsumerDetailsByPhoneNumber(@RequestParam String phoneNumber) {
		ConsumerDetails details = conServ.getConsumerDetailsByPhoneNumber(phoneNumber);
		return new ResponseEntity<>(details, HttpStatus.OK);
	}

	@GetMapping("/removeconsumeraccount")
	public ResponseEntity<?> deleteConsumerById(@RequestParam Integer consumerId) {
		String result = conServ.deleteConsumerDetailsByConsumerId(consumerId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/fetchallconsumers")
	public ResponseEntity<?> getAllConsumers() {
		List<ConsumerDetails> consumers = conServ.getAllConsumerList();
		return new ResponseEntity<>(consumers, HttpStatus.OK);
	}

	@GetMapping("/fetchconsumersbyname")
	public ResponseEntity<?> getAllConsumersByName(@RequestParam String name) {
		List<ConsumerDetails> consumers = conServ.getConsumerDetailsByFirstName(name);
		return new ResponseEntity<>(consumers, HttpStatus.OK);
	}

	@GetMapping("/fetchconsumersbypincode")
	public ResponseEntity<?> getAllConsumersByPincode(@RequestParam String pincode) {
		List<ConsumerDetails> consumers = conServ.getConsumerListByPincode(pincode);
		return new ResponseEntity<>(consumers, HttpStatus.OK);
	}

	@GetMapping("/fetchconsumersbydistrict")
	public ResponseEntity<?> getAllConsumersByDistrict(@RequestParam String district) {
		List<ConsumerDetails> consumers = conServ.getConsumerListByDistrict(district);
		return new ResponseEntity<>(consumers, HttpStatus.OK);
	}

	@GetMapping("/fetchconsumersbytown")
	public ResponseEntity<?> getAllConsumersByTown(@RequestParam String town) {
		List<ConsumerDetails> consumers = conServ.getConsumerListByTown(town);
		return new ResponseEntity<>(consumers, HttpStatus.OK);
	}

	@GetMapping("/getconsumerbyaid")
	public ResponseEntity<?> getAllConsumerByAID(@RequestParam Integer aid) {
		List<ConsumerDetails> consumers = conServ.getConsumerByAid(aid);
		return new ResponseEntity<>(consumers, HttpStatus.OK);
	}

	@GetMapping("/getdeletedconsumers")
	public ResponseEntity<?> getAllDeletedConsumers() {
		List<DeletedConsumerRecords> deletedConsumers = delServ.getDeletedAllConsumerRecords();
		return new ResponseEntity<>(deletedConsumers, HttpStatus.OK);
	}

	@GetMapping("/getdeletedconsumerbyemail")
	public ResponseEntity<?> getDeletedConsumerRecordsByEmail(@RequestParam String emailId) {
		DeletedConsumerRecords deletedRecord = delServ.getDeletedConsumerRecordByEmailId(emailId);
		return new ResponseEntity<>(deletedRecord, HttpStatus.OK);
	}

	@GetMapping("/getdeletedconsumerbyname")
	public ResponseEntity<?> getDeletedConsumerRecordsByName(@RequestParam String name) {
		List<DeletedConsumerRecords> deletedRecords = delServ.getDeletedConsumerRecordsByFirstName(name);
		return new ResponseEntity<>(deletedRecords, HttpStatus.OK);
	}

	/*
	 * Seller :
	 */

	@GetMapping("/fetchsellerbyemail")
	public ResponseEntity<?> getSellerDetailsByEmailId(@RequestParam String emailId) {
		SellerDetails details = sellServ.getSellerDetailsByEmailId(emailId);
		return new ResponseEntity<>(details, HttpStatus.OK);
	}

	@GetMapping("/fetchsellerbyusername")
	public ResponseEntity<?> getSellerDetailsByUsername(@RequestParam String username) {
		SellerDetails details = sellServ.getSellerDetailsByUsername(username);
		return new ResponseEntity<>(details, HttpStatus.OK);
	}

	@GetMapping("/fetchsellerbyphone")
	public ResponseEntity<?> getSellerDetailsByPhoneNumber(@RequestParam String phoneNumber) {
		SellerDetails details = sellServ.getSellerDetailsByPhoneNumer(phoneNumber);
		return new ResponseEntity<>(details, HttpStatus.OK);
	}

	@GetMapping("/removeselleraccount")
	public ResponseEntity<?> deleteSellerById(@RequestParam int  sellerId) {
//		int result = sellServ.deleteSellerDetailsByEmailIdOnly(emailId);
//		String msg = "Unable to delete seller...";
//		if (result == 1) {
//			msg = "Seller removed successfully";
//		}
		String msg = sellServ.deleteSellerDetailsBySellerId(sellerId);
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}

	@GetMapping("/fetchallsellers")
	public ResponseEntity<?> getAllSellers() {
		List<SellerDetails> sellers = sellServ.getAllSellerList();
		return new ResponseEntity<>(sellers, HttpStatus.OK);
	}

	@GetMapping("/fetchsellerbyname")
	public ResponseEntity<?> getAllSellersByName(@RequestParam String name) {
		List<SellerDetails> sellers = sellServ.getSellerDetailsByFirstName(name);
		return new ResponseEntity<>(sellers, HttpStatus.OK);
	}

	@GetMapping("/fetchsellersbypincode")
	public ResponseEntity<?> getAllSellersByPincode(@RequestParam String pincode) {
		List<SellerDetails> sellers = sellServ.getSellerListByPincode(pincode);
		return new ResponseEntity<>(sellers, HttpStatus.OK);
	}

	@GetMapping("/fetchsellersbydistrict")
	public ResponseEntity<?> getAllSellersByDistrict(@RequestParam String district) {
		List<SellerDetails> sellers = sellServ.getSellerListByDistrict(district);
		return new ResponseEntity<>(sellers, HttpStatus.OK);
	}

	@GetMapping("/fetchsellersbytown")
	public ResponseEntity<?> getAllSellersByTown(@RequestParam String town) {
		List<SellerDetails> sellers = sellServ.getSellerListByTown(town);
		return new ResponseEntity<>(sellers, HttpStatus.OK);
	}

	@GetMapping("/getdeletedsellers")
	public ResponseEntity<?> getAllDeletedSellers() {
		List<DeletedSellerRecords> deletedSellers = delServ.getDeletedAllSellerRecords();
		return new ResponseEntity<>(deletedSellers, HttpStatus.OK);
	}

	@GetMapping("/getdeletedsellerbyname")
	public ResponseEntity<?> getAllDeletedSellerRecordsByName(@RequestParam String name) {
		List<DeletedSellerRecords> deletedRecords = delServ.getDeletedSellerRecordsByFirstName(name);
		return new ResponseEntity<>(deletedRecords, HttpStatus.OK);
	}

	@GetMapping("/getdeletedsellerbyemail")
	public ResponseEntity<?> getDeletedSellerRecordByEmailId(@RequestParam String emailId) {
		DeletedSellerRecords deletedRecord = delServ.getDeletedSellerRecordByEmailId(emailId);
		return new ResponseEntity<>(deletedRecord, HttpStatus.OK);
	}

	/*
	 * Feedback and Query
	 */

	@GetMapping("/feedback/id")
	public ResponseEntity<?> getFeedBackById(@RequestParam Integer fid) {
		FeedBackDetails feedback = feedServ.getFeedBackDetailsById(fid);
		return new ResponseEntity<>(feedback, HttpStatus.OK);
	}

	@GetMapping("/feedback/name")
	public ResponseEntity<?> getFeedBackByName(@RequestParam String name) {
		List<FeedBackDetails> feedbackList = feedServ.getFeedBackDetailsByName(name);
		return new ResponseEntity<>(feedbackList, HttpStatus.OK);
	}

	@GetMapping("/feedback/subject")
	public ResponseEntity<?> getFeedBackBySubject(@RequestParam String subject) {
		List<FeedBackDetails> feedbackList = feedServ.getFeedBackDetailsBySubject(subject);
		return new ResponseEntity<>(feedbackList, HttpStatus.OK);
	}

	@GetMapping("/consumer/query")
	public ResponseEntity<?> getAllConsumerQueries() {
		List<ConsumerQuery> queries = queryServ.getAllConsumerQueries();
		return new ResponseEntity<>(queries, HttpStatus.OK);
	}

	@GetMapping("/consumer/queriesbyemailid")
	public ResponseEntity<?> getQueriesByConsumerEmailId(@RequestParam String emailId) {
		List<ConsumerQuery> queries = queryServ.getConsumerQueriesByConsumerEmailId(emailId);
		return new ResponseEntity<>(queries, HttpStatus.OK);
	}

	@GetMapping("/consumer/queriesbydatetime")
	public ResponseEntity<?> getConsumerQueriesByDateTime(@RequestParam String dateTime) {
		List<ConsumerQuery> queries = queryServ.getConsumerQueriesByDateTime(dateTime);
		return new ResponseEntity<>(queries, HttpStatus.OK);
	}

	@GetMapping("/seller/query")
	public ResponseEntity<?> getAllSellerQueries() {
		List<SellerQuery> queries = queryServ.getAllSellerQueries();
		return new ResponseEntity<>(queries, HttpStatus.OK);
	}

	@GetMapping("/seller/queriesbyemailid")
	public ResponseEntity<?> getQueriesBySellerEmailId(@RequestParam String emailId) {
		List<SellerQuery> queries = queryServ.getSellerQueriesBySellerEmailId(emailId);
		return new ResponseEntity<>(queries, HttpStatus.OK);
	}

	@GetMapping("/seller/queriesbydatetime")
	public ResponseEntity<?> getSellerQueriesByDateTime(@RequestParam String dateTime) {
		List<SellerQuery> queries = queryServ.getSellerQueriesByDateTime(dateTime);
		return new ResponseEntity<>(queries, HttpStatus.OK);
	}

	/*
	 * Products
	 */

	@PostMapping("/products/addnewproduct")
	public ResponseEntity<?> insertNewProductDetails(@RequestBody ProductDetails productDetails) {
		String result = proServ.insertNewProductDetails(productDetails);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@DeleteMapping("/products/removeproduct/{pid}") // changed delete mapping
	public ResponseEntity<?> removeProductByPID(@PathVariable Integer pid) throws ProductNotFoundException {
		String result = proServ.removeProductByPID(pid);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/products/getallpurchasedetails")
	public ResponseEntity<?> getAllPurchaseDetails() {
		List<PurchaseDetails> purchaseDetails = purchaseServ.getAllPurchaseRecords();
		return new ResponseEntity<>(purchaseDetails, HttpStatus.OK);
	}

	@GetMapping("/products/getpurchasedetailsbyid")
	public ResponseEntity<?> getPurchaseDetailsByPurchaseId(@RequestParam int purchaseId) {
		PurchaseDetails purchaseDetails = purchaseServ.getPurchaseDetailsByPurchaseId(purchaseId);
		return new ResponseEntity<>(purchaseDetails, HttpStatus.OK);
	}

	/*
	 * Administrator User
	 */

	@GetMapping("/getadmindetails")
	public ResponseEntity<?> getAdminDetails() {
		Administrator adminDetails = adminServ.getAdminDetails();
		return new ResponseEntity<>(adminDetails, HttpStatus.OK);
	}

	@PostMapping("/fetchdetailsbyemail")
	public ResponseEntity<?> adminLogin(@RequestBody LoginDTO login) throws IncorrectAdminDetect {
		Administrator adminDetails = adminServ.getLoginDetails(login.getEmailId(), login.getPassword());
		return new ResponseEntity<>(adminDetails, HttpStatus.OK);
	}

	@PutMapping("/changepassword") // updated for change pass put
	public ResponseEntity<?> changeAdminPassword(@RequestBody ChangePasswordDTO changePassword)
			throws UnsupportedEncodingException, UnmatchedPasswordException, IncorrectPasswordException {
		String result = adminServ.changeAdminPassword(changePassword);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
