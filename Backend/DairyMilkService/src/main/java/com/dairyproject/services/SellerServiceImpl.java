package com.dairyproject.services;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dairyproject.dto.ChangePasswordDTO;
import com.dairyproject.dto.LoginDTO;
import com.dairyproject.dto.SellerDTO;
import com.dairyproject.dto.SellerProductsDTO;
import com.dairyproject.entities.AddressDetails;
import com.dairyproject.entities.ConsumerDetails;
import com.dairyproject.entities.ProductDetails;
import com.dairyproject.entities.SellerDetails;
import com.dairyproject.exceptions.EmailAddressFoundException;
import com.dairyproject.exceptions.IncorrectPasswordException;
import com.dairyproject.exceptions.PhoneNumberFoundException;
import com.dairyproject.exceptions.ProductNotFoundException;
import com.dairyproject.exceptions.SellerNotFoundException;
import com.dairyproject.exceptions.UnmatchedPasswordException;
import com.dairyproject.exceptions.UsernameFoundException;
import com.dairyproject.repositories.AddressRepository;
import com.dairyproject.repositories.ConsumerRepository;
import com.dairyproject.repositories.SellerRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {

	@Autowired
	private AddressRepository addRepo;

	@Autowired
	private SellerRepository sellRepo;

	@Autowired
	private ConsumerRepository conRepo;

//	@Autowired
//	private DeletedSellerRepository delRepo;

	@Autowired
	private DeletedRecordsService delSelServ;

	@Autowired
	private ProductService proServ;

//	private DeletedSellerRecords delSelRecord;
	private ProductDetails proDetails;
	private SellerDetails sellDetails;
	private AddressDetails addressDetails;
	private ConsumerDetails consumerDetails;

	public SellerDetails registerNewSeller(SellerDetails sellerDetails)
			throws EmailAddressFoundException, UsernameFoundException, PhoneNumberFoundException {
		int email = sellRepo.findSellerDetailsByEmailId(sellerDetails.getEmailId());
		int username = sellRepo.findSellerDetailsByUsername(sellerDetails.getUsername());
		int phoneNumber = sellRepo.findSellerDetailsByPhoneNumber(sellerDetails.getPhoneNumber());

		// addressDetails =
		// addRepo.findAddressDetailsByPincode(sellerDetails.getAddress().getPincode());
		String encryptPassword = Base64.getEncoder().encodeToString(sellerDetails.getPassword().getBytes());
		sellerDetails.setPassword(encryptPassword);
		System.out.println(encryptPassword);
		if (email == 1) {
			throw new EmailAddressFoundException("Email address already registered");
		} else if (username == 1) {
			throw new UsernameFoundException("Username already taken, please try with another one");
		} else if (phoneNumber == 1) {
			throw new PhoneNumberFoundException("Phone Number already registered");
		} else {
			sellRepo.save(sellerDetails);
			return sellRepo.findSellerDetailsByEmailPass(sellerDetails.getEmailId(), sellerDetails.getPassword());
		}
	}

//	else if (addressDetails != null) {
//		sellerDetails.setAddress(addressDetails);
//		sellRepo.save(sellerDetails);
//		addressDetails = null;
//		return sellRepo.findSellerDetailsByEmailPass(sellerDetails.getEmailId(),
//				sellerDetails.getPassword());
//	}
	public SellerDetails getSellerDetailsByEmailAndPassword(String emailId, String password)
			throws UnsupportedEncodingException {
		String encryptPassword = Base64.getEncoder().encodeToString(password.getBytes("UTF-8"));
		SellerDetails s = sellRepo.findSellerDetailsByEmailPass(emailId, encryptPassword);
		System.out.println(s);
		return s;
	}

	public SellerDetails getSellerDetailsByUsernameAndPassword(String username, String password) {
		return sellRepo.findSellerDetailsByUsernameAndPassword(username, password);
	}

	public SellerDetails getSellerDetailsByPhoneNumberAndPassword(String phoneNumber, String password) {
		return sellRepo.findSellerDetailsByPhoneNumberAndPassword(phoneNumber, password);
	}

	public SellerDetails getSellerDetailsByEmailId(String emailId) {
		return sellRepo.findSellerDetailsByEmailIdOnly(emailId);
	}

	public SellerDetails getSellerDetailsByUsername(String username) {
		return sellRepo.findSellerDetailsByUsernameOnly(username);
	}

	public SellerDetails getSellerDetailsByPhoneNumer(String phoneNumber) {
		return sellRepo.findSellerDetailsByPhoneNumberOnly(phoneNumber);
	}

	public String deleteSellerDetailsByEmailId(LoginDTO login) throws UnsupportedEncodingException {
		return delSelServ.deleteSellerByEmailId(login);
	}

//	public String deleteSellerDetailsBySellerId(Integer sellerId) {
//		String i = delSelServ.deleteSellerBySellerId(sellerId);
//		System.out.println(i);
//		return i;
//	}

	public List<SellerDetails> getAllSellerList() {
		return sellRepo.findAllSellerDetails();
	}

	public List<SellerDetails> getSellerDetailsByFirstName(String firstName) {
		return sellRepo.findSellerByName(firstName);
	}

	public List<SellerDetails> getSellerListByPincode(String pincode) {
		return sellRepo.findSellersByPincode(pincode);
	}

	public List<SellerDetails> getSellerListByDistrict(String district) {
		return sellRepo.findSellersByDistrict(district);
	}

	public List<SellerDetails> getSellerListByTown(String town) {
		return sellRepo.findSellersByTown(town);
	}

	public Set<SellerDetails> getSellerListByLocality(String emailId) {

		consumerDetails = conRepo.findConsumerDetailsByEmailIdOnly(emailId);
		List<SellerDetails> sellersByPincode = sellRepo.findSellersByPincode(consumerDetails.getAddress().getPincode());
		List<SellerDetails> sellersByTown = sellRepo.findSellersByTown(consumerDetails.getAddress().getTown());
		List<SellerDetails> sellersByDistrict = sellRepo
				.findSellersByDistrict(consumerDetails.getAddress().getDistrict());

		Set<SellerDetails> concatinatedSellerSet = new HashSet<>();
		concatinatedSellerSet.addAll(sellersByPincode);
		concatinatedSellerSet.addAll(sellersByTown);
		concatinatedSellerSet.addAll(sellersByDistrict);

		return concatinatedSellerSet;

	}

	private SellerDTO convertToDTO(SellerDetails sellerDetails) {
		if (sellerDetails == null) {
			return null;
		}
		System.out.println("Converting SellerDetails to SellerDTO: " + sellerDetails.getFirstName());

		SellerDTO dto = new SellerDTO();
		dto.setEmailId(sellerDetails.getEmailId());
		dto.setFirstName(sellerDetails.getFirstName());
		dto.setLastName(sellerDetails.getLastName());

		if (sellerDetails.getAddress() != null) {
			dto.setTown(sellerDetails.getAddress().getTown());
			dto.setDistrict(sellerDetails.getAddress().getDistrict());
			dto.setPincode(sellerDetails.getAddress().getPincode());

		} else {
			System.out.println("Address is NULL for seller: " + sellerDetails.getFirstName());
		}
		System.out.println(dto);
		return dto;
	}

//	public SellerDTO getSellerDetailsDTOByEmailId(String emailId) {
//        SellerDetails sellerDetails = sellRepo.findSellerDetailsByEmailIdOnly(emailId);
//        return convertToDTO(sellerDetails);
//    }

//	public Set<SellerDTO> getSellersByProductAndLocality(String emailId, String productName) {
//
//		consumerDetails = conRepo.findConsumerDetailsByEmailIdOnly(emailId);
//
//		List<ProductDetails> productDetails = new ArrayList<>(proServ.getAllProductDetails());
//		List<SellerDetails> sellerList = new ArrayList<>();
//
//		for (int i = 0; i < productDetails.size(); i++) {
//			if (productDetails.get(i).getName().equals(productName)) {
//				sellerList = new ArrayList<>(productDetails.get(i).getSellerDetails());
//				break;
//
//			}
//		}
//
//		Set<SellerDTO> sellerFinalList = new HashSet<>();
//
//		for (int i = 0; i < sellerList.size(); i++) {
//			if (sellerList.get(i).getAddress().getPincode().equals(consumerDetails.getAddress().getPincode())) {
//				sellerFinalList.add(convertToDTO(sellerList.get(i)));
//			}
//
//			if (sellerList.get(i).getAddress().getTown().equals(consumerDetails.getAddress().getTown())) {
//				sellerFinalList.add(convertToDTO(sellerList.get(i)));
//			}
//
//			if (sellerList.get(i).getAddress().getDistrict().equals(consumerDetails.getAddress().getDistrict())) {
//				sellerFinalList.add(convertToDTO(sellerList.get(i)));
//			}
//		}
//		consumerDetails = null;
//		productDetails = null;
//		sellerList = null;
//		return sellerFinalList;
//
//	}

	@Transactional
	public Set<SellerDTO> getSellersByProductAndLocality(String emailId, String productName) {
		System.out.println(productName);
		ConsumerDetails consumerDetails = conRepo.findConsumerDetailsByEmailIdOnly(emailId);
		ProductDetails product = proServ.getProductDetailsByName(productName);
		System.out.println(product);

		if (product == null || consumerDetails == null) {
			return Collections.emptySet();
		}

		Set<SellerDetails> sellers = product.getSellerDetails();
		System.out.println(sellers);
		if (sellers == null || sellers.isEmpty()) {
			return Collections.emptySet();
		}

		String pincode = consumerDetails.getAddress().getPincode();
		System.out.println(pincode);
		String town = consumerDetails.getAddress().getTown();
		System.out.println(town);
		String district = consumerDetails.getAddress().getDistrict();
		System.out.println(district);
		String state = consumerDetails.getAddress().getState();
		Set<SellerDTO> sellerFinalList = new HashSet<>();

		for (SellerDetails seller : sellers) {
			AddressDetails address = seller.getAddress();
			System.out.println("Comparing consumer address with seller address: ");
			System.out.println("Consumer - Pincode: " + pincode + ", Town: " + town + ", District: " + district);
			System.out.println("Seller - Pincode: " + address.getPincode() + ", Town: " + address.getTown()
					+ ", District: " + address.getDistrict());

			if ((address.getDistrict().equals(district) && address.getState().equals(state))) {

				SellerDTO sellerDTO = convertToDTO(seller);

				// Prevent recursive looping by checking existing entries
				if (!sellerFinalList.contains(sellerDTO)) {
					sellerFinalList.add(sellerDTO);
				}
			}
			System.out.println(sellerFinalList);
		}

		return sellerFinalList;
	}

	public SellerDetails updateSellerDetails(SellerDetails sellerDetails) {
		sellDetails = sellRepo.findSellerDetailsByEmailIdOnly(sellerDetails.getEmailId());

		addressDetails = addRepo.findAddressDetailsByPincode(sellerDetails.getAddress().getPincode());
		if (addressDetails != null) {
			sellDetails.setAddress(addressDetails);
		} else {
			addRepo.save(sellerDetails.getAddress());
			addressDetails = addRepo.findAddressDetailsByPincode(sellerDetails.getAddress().getPincode());
			sellDetails.setAddress(addressDetails);
		}

		sellDetails.setStreet(sellerDetails.getStreet());
		sellDetails.setFirstName(sellerDetails.getFirstName());
		sellDetails.setLastName(sellerDetails.getLastName());
		sellDetails.setPhoneNumber(sellerDetails.getPhoneNumber());
		sellDetails.setUsername(sellerDetails.getUsername());
		sellRepo.save(sellDetails);

		return sellRepo.findSellerDetailsByEmailAndPassword(sellerDetails.getEmailId(), sellerDetails.getPassword());

	}

	public String changeSellerPassword(ChangePasswordDTO changePassword)
			throws UnsupportedEncodingException, IncorrectPasswordException, UnmatchedPasswordException {
		if (changePassword.getNewPassword().equals(changePassword.getConfirmPassword())) {
			String encryptPassword = Base64.getEncoder()
					.encodeToString(changePassword.getOldPassword().getBytes("UTF-8"));
			String newEncryptPassword = Base64.getEncoder()
					.encodeToString(changePassword.getNewPassword().getBytes("UTF-8"));
			sellDetails = sellRepo.findSellerDetailsByEmailAndPassword(changePassword.getEmailId(), encryptPassword);
			if (sellDetails != null && sellDetails.getPassword().equals(encryptPassword)) {
				sellDetails.setPassword(newEncryptPassword);
				sellRepo.save(sellDetails);
				return "Password changed successfully.";
			} else {
				throw new IncorrectPasswordException("Incorrect old password !");
			}

		} else {
			throw new UnmatchedPasswordException("Password does not match. Please enter correct password !");
		}

	}

	public Set<ProductDetails> addProducts(SellerProductsDTO sellerProducts)
			throws ProductNotFoundException, SellerNotFoundException {
		String emailId = sellerProducts.getEmailId();
		sellDetails = sellRepo.findSellerDetailsByEmailIdOnly(sellerProducts.getEmailId());
		System.out.println("Email ID: " + emailId);
		System.out.println(sellerProducts.getProductDetails());
		System.out.println(sellDetails);
		if (sellDetails != null) {
			List<String> list = new ArrayList<String>(sellerProducts.getProductDetails());

			Set<ProductDetails> productDetailsSet = sellDetails.getProductDetails();
			System.out.println(productDetailsSet);
			Set<SellerDetails> sellerList = new HashSet<>();

			for (int i = 0; i < list.size(); i++) {
				System.out.println("Fetching product: " + list.get(i));
				String pname = list.get(i);
				proDetails = proServ.getProductDetailsByName(pname);
				System.out.println(proDetails);
				if (proDetails != null) {
					sellerList = proDetails.getSellerDetails();
					sellerList.add(sellDetails);
					proDetails.setSellerDetails(sellerList);
					proServ.updateProductDetails(proDetails);
					productDetailsSet.add(proDetails);
				} else {
					throw new ProductNotFoundException(list.get(i) + " product not found !");
				}
			}
			sellDetails.setProductDetails(productDetailsSet);
			sellRepo.save(sellDetails);
			proDetails = null;
			sellerList = null;
			productDetailsSet = null;
			sellDetails = null;
			return sellRepo.findSellerDetailsByEmailIdOnly(sellerProducts.getEmailId()).getProductDetails();
		}

		throw new SellerNotFoundException("Seller not found !");

	}

	public Set<ProductDetails> getSellerAllProductDetails(String emailId) throws SellerNotFoundException {
		sellDetails = sellRepo.findSellerDetailsByEmailIdOnly(emailId);
		if (sellDetails != null) {
			return sellDetails.getProductDetails();
		}

		throw new SellerNotFoundException("Seller not found !");
	}

	public List<SellerDetails> getSellersByProductName(String name) {
		proDetails = proServ.getProductDetailsByName(name);
		return null;
	}

	public String removeProductFromList(String emailId, Integer pid) throws ProductNotFoundException {
		sellDetails = sellRepo.findSellerDetailsByEmailIdOnly(emailId);
		proDetails = proServ.getProductDetailsByPid(pid);

		List<ProductDetails> productDetails = new ArrayList<>(sellDetails.getProductDetails());

		if (productDetails.remove(proDetails)) {
			sellDetails.setProductDetails(new HashSet<>(productDetails));
			sellRepo.save(sellDetails);
			return "Product removed from your list !";
		}

		throw new ProductNotFoundException("Product not found !");
	}

	@Override
	public int deleteSellerDetailsByEmailIdOnly(String emailId) {

		return sellRepo.deleteSellerDetailsByEmailId(emailId);
	}

	@Override
	public String deleteSellerDetailsBySellerId(int sellerId) {

		return delSelServ.deleteSellerBySellerId(sellerId);
	}
}
